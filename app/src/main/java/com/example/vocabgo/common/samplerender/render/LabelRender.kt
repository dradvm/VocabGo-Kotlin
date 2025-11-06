package com.example.vocabgo.common.samplerender.render

import com.example.vocabgo.common.samplerender.Mesh
import com.example.vocabgo.common.samplerender.SampleRender
import com.example.vocabgo.common.samplerender.Shader
import com.example.vocabgo.common.samplerender.VertexBuffer
import com.google.ar.core.Pose
import java.nio.ByteBuffer
import java.nio.ByteOrder

class LabelRender {
    companion object {
        private const val TAG = "LabelRender"
        val COORDS_BUFFER_SIZE = 2 * 4 * 4
        val NDC_QUAD_COORDS_BUFFER =
            ByteBuffer.allocateDirect(COORDS_BUFFER_SIZE)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(
                    floatArrayOf(
                        -1.5f, -1.5f,
                        1.5f, -1.5f,
                        -1.5f, 1.5f,
                        1.5f, 1.5f
                    )
                )
        val SQUARE_TEX_COORDS_BUFFER =
            ByteBuffer.allocateDirect(COORDS_BUFFER_SIZE)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(
                    floatArrayOf(
                        0f, 0f,
                        1f, 0f,
                        0f, 1f,
                        1f, 1f
                    )
                )
    }

    val cache = TextTextureCache()
    lateinit var mesh: Mesh
    lateinit var shader: Shader
    val labelOrigin = FloatArray(3)

    fun onSurfaceCreated(render: SampleRender) {
        shader =
            Shader.createFromAssets(render, "shaders/label.vert", "shaders/label.frag", null)
                .setBlend(
                    Shader.BlendFactor.ONE,
                    Shader.BlendFactor.ONE_MINUS_SRC_ALPHA
                )
                .setDepthTest(true)
                .setDepthWrite(true)

        val vertexBuffers = arrayOf(
            VertexBuffer(render, 2, NDC_QUAD_COORDS_BUFFER),
            VertexBuffer(render, 2, SQUARE_TEX_COORDS_BUFFER)
        )
        mesh = Mesh(render, Mesh.PrimitiveMode.TRIANGLE_STRIP, null, vertexBuffers)
    }

    fun draw(
        render: SampleRender,
        viewProjectionMatrix: FloatArray,
        pose: Pose,
        cameraPose: Pose,
        label: String
    ) {
        labelOrigin[0] = pose.tx()
        labelOrigin[1] = pose.ty()
        labelOrigin[2] = pose.tz()
        shader
            .setMat4("u_ViewProjection", viewProjectionMatrix)
            .setVec3("u_LabelOrigin", labelOrigin)
            .setVec3("u_CameraPos", cameraPose.translation)
            .setTexture("uTexture", cache.get(render, label))
        render.draw(mesh, shader)
    }
}
