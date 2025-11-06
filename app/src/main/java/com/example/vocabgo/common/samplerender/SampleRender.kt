package com.example.vocabgo.common.samplerender

import android.content.res.AssetManager
import android.opengl.GLES30
import android.opengl.GLES32
import android.opengl.GLSurfaceView
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class SampleRender(
    private val glSurfaceView: GLSurfaceView,
    private val renderer: Renderer,
    assetManager: AssetManager
) {
    private val _assetManager: AssetManager = assetManager
    val assetManager: AssetManager get() = _assetManager
    var viewportWidth = 1;
    var viewportHeight = 1;

    companion object {
        private val TAG = Texture::class.java.simpleName


        interface Renderer {
            fun onSurfaceCreated(render: SampleRender)
            fun onSurfaceChanged(render: SampleRender, width: Int, height: Int)
            fun onDrawFrame(render: SampleRender)
        }
    }

    init {
        glSurfaceView.preserveEGLContextOnPause = true
        glSurfaceView.setEGLContextClientVersion(3)
        glSurfaceView.setEGLConfigChooser(8,8,8,8,16,0)
        glSurfaceView.setRenderer(object : GLSurfaceView.Renderer {

            override fun onSurfaceCreated(gl: GL10, config: EGLConfig) {
                GLES32.glEnable(GLES32.GL_BLEND)
                GLError.maybeThrowGLException("Failed to enable blending", "glEnable")
                renderer.onSurfaceCreated(this@SampleRender)
            }

            override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
                viewportWidth = width
                viewportHeight = height
                renderer.onSurfaceChanged(this@SampleRender, width, height)
            }

            override fun onDrawFrame(gl: GL10?) {
                clear(framebuffer = null, r = 0f, g = 0f, b = 0f, a = 1f)
                renderer.onDrawFrame(this@SampleRender)
            }
        })

    }

    fun draw(mesh: Mesh, shader: Shader) {
        draw(mesh, shader, null)
    }

    fun draw(mesh: Mesh, shader: Shader, frameBuffer: Framebuffer?) {
        useFramebuffer(frameBuffer)
        shader.lowLevelUse()
        mesh.lowLevelDraw()
    }

    fun clear(framebuffer: Framebuffer?, r: Float, g: Float, b: Float, a: Float) {
        useFramebuffer(framebuffer)
        GLES32.glClearColor(r, g, b, a)
        GLError.maybeThrowGLException("Failed to set clear color", "glClearColor")
        GLES32.glDepthMask(true)
        GLError.maybeThrowGLException("Failed to set depth write mask", "glDepthMask")
        GLES32.glClear(GLES32.GL_COLOR_BUFFER_BIT or GLES32.GL_DEPTH_BUFFER_BIT)
        GLError.maybeThrowGLException("Failed to clear framebuffer", "glClear")
    }

    private fun useFramebuffer(framebuffer: Framebuffer?) {
        val framebufferId: Int
        var viewportWidth: Int
        var viewportHeight: Int
        if (framebuffer == null) {
            framebufferId = 0
            viewportWidth = this.viewportWidth
            viewportHeight = this.viewportHeight
        } else {
            framebufferId = framebuffer.getFramebufferId()
            viewportWidth = framebuffer.width
            viewportHeight = framebuffer.height
        }
        GLES30.glBindFramebuffer(GLES30.GL_FRAMEBUFFER, framebufferId)
        GLError.maybeThrowGLException("Failed to bind framebuffer", "glBindFramebuffer")
        GLES30.glViewport(0, 0, viewportWidth, viewportHeight)
        GLError.maybeThrowGLException("Failed to set viewport dimensions", "glViewport")
    }
}