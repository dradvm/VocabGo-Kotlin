package com.example.vocabgo.common.samplerender.render

import android.media.Image
import android.opengl.GLES32
import com.example.vocabgo.common.samplerender.Framebuffer
import com.example.vocabgo.common.samplerender.Mesh
import com.example.vocabgo.common.samplerender.SampleRender
import com.example.vocabgo.common.samplerender.Shader
import com.example.vocabgo.common.samplerender.Texture
import com.example.vocabgo.common.samplerender.VertexBuffer
import com.google.ar.core.Coordinates2d
import com.google.ar.core.Frame
import java.io.IOException
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer

class BackgroundRenderer(render: SampleRender) {
    companion object {
        private const val COORDS_BUFFER_SIZE = 2 * 4 * 4
        private val NDC_QUAD_COORDS_BUFFER: FloatBuffer =
            ByteBuffer.allocateDirect(COORDS_BUFFER_SIZE).order(ByteOrder.nativeOrder()).asFloatBuffer()
        private val VIRTUAL_SCENE_TEX_COORDS_BUFFER: FloatBuffer =
            ByteBuffer.allocateDirect(COORDS_BUFFER_SIZE).order(ByteOrder.nativeOrder()).asFloatBuffer()

        init {
            NDC_QUAD_COORDS_BUFFER.put(floatArrayOf(-1f, -1f, 1f, -1f, -1f, 1f, 1f, 1f))
            VIRTUAL_SCENE_TEX_COORDS_BUFFER.put(floatArrayOf(0f, 0f, 1f, 0f, 0f, 1f, 1f, 1f))
        }
    }

    private val cameraTexCoords: FloatBuffer =
        ByteBuffer.allocateDirect(COORDS_BUFFER_SIZE).order(ByteOrder.nativeOrder()).asFloatBuffer()

    private val mesh: Mesh
    private val cameraTexCoordsVertexBuffer: VertexBuffer
    private var backgroundShader: Shader? = null
    private var occlusionShader: Shader? = null
    private val _cameraDepthTexture: Texture
    private val _cameraColorTexture: Texture
    private var depthColorPaletteTexture: Texture? = null

    private var useDepthVisualization = false
    private var useOcclusion = false
    private var aspectRatio = 0f

    init {
        _cameraColorTexture =
            Texture(render, Texture.Target.TEXTURE_EXTERNAL_OES, Texture.WrapMode.CLAMP_TO_EDGE, false)
        _cameraDepthTexture = Texture(render, Texture.Target.TEXTURE_2D, Texture.WrapMode.CLAMP_TO_EDGE, false)

        val screenCoordsVertexBuffer = VertexBuffer(render, 2, NDC_QUAD_COORDS_BUFFER)
        cameraTexCoordsVertexBuffer = VertexBuffer(render, 2, null)
        val virtualSceneTexCoordsVertexBuffer = VertexBuffer(render, 2, VIRTUAL_SCENE_TEX_COORDS_BUFFER)
        mesh = Mesh(render, Mesh.PrimitiveMode.TRIANGLE_STRIP, null, arrayOf(screenCoordsVertexBuffer, cameraTexCoordsVertexBuffer, virtualSceneTexCoordsVertexBuffer))
    }

    @Throws(IOException::class)
    fun setUseDepthVisualization(render: SampleRender, useDepthVisualization: Boolean) {
        if (backgroundShader != null) {
            if (this.useDepthVisualization == useDepthVisualization) return
            backgroundShader?.close()
            backgroundShader = null
            this.useDepthVisualization = useDepthVisualization
        }
        if (useDepthVisualization) {
            depthColorPaletteTexture = Texture.createTextureFromAsset(render, "models/depth_color_palette.png", Texture.WrapMode.CLAMP_TO_EDGE, Texture.ColorFormat.LINEAR)
            backgroundShader = Shader.createFromAssets(render, "shaders/background_show_depth_color_visualization.vert", "shaders/background_show_depth_color_visualization.frag", null)
                .setTexture("u_CameraDepthTexture", _cameraDepthTexture)
                .setTexture("u_ColorMap", depthColorPaletteTexture!!)
                .setDepthTest(false)
                .setDepthWrite(false)
        } else {
            backgroundShader = Shader.createFromAssets(render, "shaders/background_show_camera.vert", "shaders/background_show_camera.frag", null)
                .setTexture("u_CameraColorTexture", _cameraColorTexture)
                .setDepthTest(false)
                .setDepthWrite(false)
        }
    }

    @Throws(IOException::class)
    fun setUseOcclusion(render: SampleRender, useOcclusion: Boolean) {
        if (occlusionShader != null) {
            if (this.useOcclusion == useOcclusion) return
            occlusionShader?.close()
            occlusionShader = null
            this.useOcclusion = useOcclusion
        }
        val defines = hashMapOf<String, String>("USE_OCCLUSION" to if (useOcclusion) "1" else "0")
        occlusionShader = Shader.createFromAssets(render, "shaders/occlusion.vert", "shaders/occlusion.frag", defines)
            .setDepthTest(false)
            .setDepthWrite(false)
            .setBlend(Shader.BlendFactor.SRC_ALPHA, Shader.BlendFactor.ONE_MINUS_SRC_ALPHA)
        if (useOcclusion) {
            occlusionShader?.setTexture("u_CameraDepthTexture", _cameraDepthTexture)
                ?.setFloat("u_DepthAspectRatio", aspectRatio)
        }
    }

    fun updateDisplayGeometry(frame: Frame) {
        if (frame.hasDisplayGeometryChanged()) {
            frame.transformCoordinates2d(
                Coordinates2d.OPENGL_NORMALIZED_DEVICE_COORDINATES,
                NDC_QUAD_COORDS_BUFFER,
                Coordinates2d.TEXTURE_NORMALIZED,
                cameraTexCoords
            )
            cameraTexCoordsVertexBuffer.set(cameraTexCoords)
        }
    }

    fun updateCameraDepthTexture(image: Image) {
        GLES32.glBindTexture(GLES32.GL_TEXTURE_2D, _cameraDepthTexture.textureId)
        GLES32.glTexImage2D(
            GLES32.GL_TEXTURE_2D,
            0,
            GLES32.GL_RG8,
            image.width,
            image.height,
            0,
            GLES32.GL_RG,
            GLES32.GL_UNSIGNED_BYTE,
            image.planes[0].buffer
        )
        if (useOcclusion) {
            aspectRatio = image.width.toFloat() / image.height.toFloat()
            occlusionShader?.setFloat("u_DepthAspectRatio", aspectRatio)
        }
    }

    fun drawBackground(render: SampleRender) {
        backgroundShader?.let { render.draw(mesh, it) }
    }

    fun drawVirtualScene(render: SampleRender, virtualSceneFramebuffer: Framebuffer, zNear: Float, zFar: Float) {
        occlusionShader?.setTexture("u_VirtualSceneColorTexture", virtualSceneFramebuffer.colorTexture)
        if (useOcclusion) {
            occlusionShader?.setTexture("u_VirtualSceneDepthTexture", virtualSceneFramebuffer.depthTexture)
                ?.setFloat("u_ZNear", zNear)
                ?.setFloat("u_ZFar", zFar)
        }
        occlusionShader?.let { render.draw(mesh, it) }
    }

    val cameraColorTexture: Texture
        get() = _cameraColorTexture

    val cameraDepthTexture: Texture
        get() = _cameraDepthTexture
}
