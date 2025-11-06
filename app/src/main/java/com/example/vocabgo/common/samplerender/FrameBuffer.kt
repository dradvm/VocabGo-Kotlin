package com.example.vocabgo.common.samplerender

import android.opengl.GLES32
import android.util.Log
import java.io.Closeable

class Framebuffer(render: SampleRender, width: Int, height: Int) : Closeable {

    private val framebufferId = IntArray(1) { 0 }
    val colorTexture: Texture
    val depthTexture: Texture
    var width: Int = -1
        private set
    var height: Int = -1
        private set

    init {
        try {
            colorTexture = Texture(render, Texture.Target.TEXTURE_2D, Texture.WrapMode.CLAMP_TO_EDGE, false)
            depthTexture = Texture(render, Texture.Target.TEXTURE_2D, Texture.WrapMode.CLAMP_TO_EDGE, false)

            GLES32.glBindTexture(GLES32.GL_TEXTURE_2D, depthTexture.textureId)
            GLError.maybeThrowGLException("Failed to bind depth texture", "glBindTexture")
            GLES32.glTexParameteri(GLES32.GL_TEXTURE_2D, GLES32.GL_TEXTURE_COMPARE_MODE, GLES32.GL_NONE)
            GLError.maybeThrowGLException("Failed to set texture parameter", "glTexParameteri")
            GLES32.glTexParameteri(GLES32.GL_TEXTURE_2D, GLES32.GL_TEXTURE_MIN_FILTER, GLES32.GL_NEAREST)
            GLError.maybeThrowGLException("Failed to set texture parameter", "glTexParameteri")
            GLES32.glTexParameteri(GLES32.GL_TEXTURE_2D, GLES32.GL_TEXTURE_MAG_FILTER, GLES32.GL_NEAREST)
            GLError.maybeThrowGLException("Failed to set texture parameter", "glTexParameteri")

            resize(width, height)

            GLES32.glGenFramebuffers(1, framebufferId, 0)
            GLError.maybeThrowGLException("Framebuffer creation failed", "glGenFramebuffers")
            GLES32.glBindFramebuffer(GLES32.GL_FRAMEBUFFER, framebufferId[0])
            GLError.maybeThrowGLException("Failed to bind framebuffer", "glBindFramebuffer")
            GLES32.glFramebufferTexture2D(
                GLES32.GL_FRAMEBUFFER,
                GLES32.GL_COLOR_ATTACHMENT0,
                GLES32.GL_TEXTURE_2D,
                colorTexture.textureId,
                0
            )
            GLError.maybeThrowGLException("Failed to bind color texture to framebuffer", "glFramebufferTexture2D")
            GLES32.glFramebufferTexture2D(
                GLES32.GL_FRAMEBUFFER,
                GLES32.GL_DEPTH_ATTACHMENT,
                GLES32.GL_TEXTURE_2D,
                depthTexture.textureId,
                0
            )
            GLError.maybeThrowGLException("Failed to bind depth texture to framebuffer", "glFramebufferTexture2D")

            val status = GLES32.glCheckFramebufferStatus(GLES32.GL_FRAMEBUFFER)
            if (status != GLES32.GL_FRAMEBUFFER_COMPLETE) {
                throw IllegalStateException("Framebuffer construction not complete: code $status")
            }
        } catch (t: Throwable) {
            close()
            throw t
        }
    }

    override fun close() {
        if (framebufferId[0] != 0) {
            GLES32.glDeleteFramebuffers(1, framebufferId, 0)
            GLError.maybeLogGLError(Log.WARN, TAG, "Failed to free framebuffer", "glDeleteFramebuffers")
            framebufferId[0] = 0
        }
        colorTexture.close()
        depthTexture.close()
    }

    fun resize(width: Int, height: Int) {
        if (this.width == width && this.height == height) return
        this.width = width
        this.height = height

        GLES32.glBindTexture(GLES32.GL_TEXTURE_2D, colorTexture.textureId)
        GLError.maybeThrowGLException("Failed to bind color texture", "glBindTexture")
        GLES32.glTexImage2D(
            GLES32.GL_TEXTURE_2D,
            0,
            GLES32.GL_RGBA,
            width,
            height,
            0,
            GLES32.GL_RGBA,
            GLES32.GL_UNSIGNED_BYTE,
            null
        )
        GLError.maybeThrowGLException("Failed to specify color texture format", "glTexImage2D")

        GLES32.glBindTexture(GLES32.GL_TEXTURE_2D, depthTexture.textureId)
        GLError.maybeThrowGLException("Failed to bind depth texture", "glBindTexture")
        GLES32.glTexImage2D(
            GLES32.GL_TEXTURE_2D,
            0,
            GLES32.GL_DEPTH_COMPONENT32F,
            width,
            height,
            0,
            GLES32.GL_DEPTH_COMPONENT,
            GLES32.GL_FLOAT,
            null
        )
        GLError.maybeThrowGLException("Failed to specify depth texture format", "glTexImage2D")
    }

    fun getFramebufferId() = framebufferId[0]

    companion object {
        private val TAG = Framebuffer::class.java.simpleName
    }
}
