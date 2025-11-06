package com.example.vocabgo.common.samplerender

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.opengl.GLES11Ext
import android.opengl.GLES32
import android.util.Log
import java.io.Closeable
import java.nio.ByteBuffer

class Texture(
    render: SampleRender,
    target: Target,
    wrapMode: WrapMode,
    useMipmaps: Boolean = true
) : Closeable {
    companion object {
        private val TAG = Texture::class.java.simpleName

        fun createTextureFromAsset(
            render: SampleRender,
            assetFileName: String,
            wrapMode: WrapMode,
            colorFormat: ColorFormat
        ): Texture {
            val texture = Texture(render, Texture.Target.TEXTURE_2D, wrapMode)
            var bitmap: Bitmap? = null
            try {
                bitmap = convertBitmapToConfig(
                    BitmapFactory.decodeStream(render.assetManager.open(assetFileName)),
                    Bitmap.Config.ARGB_8888
                )
                val buffer = ByteBuffer.allocateDirect(bitmap.byteCount)
                bitmap.copyPixelsToBuffer(buffer)
                buffer.rewind()

                GLES32.glBindTexture(GLES32.GL_TEXTURE_2D, texture._textureId[0])
                GLError.maybeThrowGLException("Failed to bind texture", "glBindTexture")

                GLES32.glTexImage2D(
                    GLES32.GL_TEXTURE_2D,
                    0,
                    colorFormat.glesEnum,
                    bitmap.width,
                    bitmap.height,
                    0,
                    GLES32.GL_RGBA,
                    GLES32.GL_UNSIGNED_BYTE,
                    buffer
                )
                GLError.maybeThrowGLException("Failed to populate texture data", "glTexImage2D")

                GLES32.glGenerateMipmap(GLES32.GL_TEXTURE_2D)
                GLError.maybeThrowGLException("Failed to generate mipmaps", "glGenerateMipmap")
            } catch (t: Throwable) {
                texture.close()
                throw t
            } finally {
                bitmap?.recycle()
            }
            return texture
        }

        private fun convertBitmapToConfig(bitmap: Bitmap, config: Bitmap.Config): Bitmap {
            if (bitmap.config == config) {
                return bitmap
            }
            val result = bitmap.copy(config, false)
            bitmap.recycle()
            return result
        }

    }
    private val _textureId = intArrayOf(0)

    val textureId: Int get() = _textureId.first()

    private var _target: Target = target

    val target: Target get() = _target
    init {

        GLES32.glGenTextures(1, _textureId, 0)
        GLError.maybeThrowGLException("Texture creation failed", "glGenTextures")

        val minFilter = if (useMipmaps) GLES32.GL_LINEAR_MIPMAP_LINEAR else GLES32.GL_LINEAR

        try {
            GLES32.glBindTexture(target.glesEnum, _textureId[0])
            GLError.maybeThrowGLException("Failed to bind texture", "glBindTexture")

            GLES32.glTexParameteri(target.glesEnum, GLES32.GL_TEXTURE_MIN_FILTER, minFilter)
            GLError.maybeThrowGLException("Failed to set texture parameter", "glTexParameteri")

            GLES32.glTexParameteri(target.glesEnum, GLES32.GL_TEXTURE_MAG_FILTER, GLES32.GL_LINEAR)
            GLError.maybeThrowGLException("Failed to set texture parameter", "glTexParameteri")

            GLES32.glTexParameteri(target.glesEnum, GLES32.GL_TEXTURE_WRAP_S, wrapMode.glesEnum)
            GLError.maybeThrowGLException("Failed to set texture parameter", "glTexParameteri")

            GLES32.glTexParameteri(target.glesEnum, GLES32.GL_TEXTURE_WRAP_T, wrapMode.glesEnum)
            GLError.maybeThrowGLException("Failed to set texture parameter", "glTexParameteri")

        } catch (t: Throwable) {
            close()
            throw t
        }
    }
    enum class WrapMode(val glesEnum: Int) {
        CLAMP_TO_EDGE(GLES32.GL_CLAMP_TO_EDGE),
        MIRRORED_REPEAT(GLES32.GL_MIRRORED_REPEAT),
        REPEAT(GLES32.GL_REPEAT)
    }

    enum class Target(val glesEnum: Int) {
        TEXTURE_2D(GLES32.GL_TEXTURE_2D),
        TEXTURE_EXTERNAL_OES(GLES11Ext.GL_TEXTURE_EXTERNAL_OES),
        TEXTURE_CUBE_MAP(GLES32.GL_TEXTURE_CUBE_MAP)
    }

    enum class ColorFormat(val glesEnum: Int) {
        LINEAR(GLES32.GL_RGBA8),
        SRGB(GLES32.GL_SRGB8_ALPHA8)
    }

    override fun close() {
        if (_textureId.first() != 0) {
            GLES32.glDeleteTextures(1,_textureId, 0)
            GLError.maybeLogGLError(Log.WARN, TAG, "Failed to free texture", "glDeleteTextures")
            _textureId.set(0, 0)
        }
    }


}