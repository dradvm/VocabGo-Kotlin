package com.example.vocabgo.common.samplerender

import android.opengl.GLES32
import android.util.Log
import java.nio.Buffer

class GpuBuffer(
    private val target: Int,
    private val numberOfBytesPerEntry: Int,
    entries: Buffer?
) {
    companion object {
        const val TAG = "GpuBuffer"
        const val INT_SIZE = 4
        const val FLOAT_SIZE = 4
    }

    private val bufferId = IntArray(1)
    private var size: Int = 0
    private var capacity: Int = 0

    init {
        var initialEntries = entries
        if (initialEntries != null) {
            if (!initialEntries.isDirect) {
                throw IllegalArgumentException("If non-null, entries buffer must be a direct buffer")
            }
            if (initialEntries.limit() == 0) {
                initialEntries = null
            }
        }

        size = initialEntries?.limit() ?: 0
        capacity = initialEntries?.limit() ?: 0

        try {
            GLES32.glBindVertexArray(0)
            GLError.maybeThrowGLException("Failed to unbind vertex array", "glBindVertexArray")

            GLES32.glGenBuffers(1, bufferId, 0)
            GLError.maybeThrowGLException("Failed to generate buffers", "glGenBuffers")

            GLES32.glBindBuffer(target, bufferId[0])
            GLError.maybeThrowGLException("Failed to bind buffer object", "glBindBuffer")

            initialEntries?.let {
                it.rewind()
                GLES32.glBufferData(target, it.limit() * numberOfBytesPerEntry, it, GLES32.GL_DYNAMIC_DRAW)
            }
            GLError.maybeThrowGLException("Failed to populate buffer object", "glBufferData")
        } catch (t: Throwable) {
            free()
            throw t
        }
    }

    fun set(entries: Buffer?) {
        if (entries == null || entries.limit() == 0) {
            size = 0
            return
        }
        if (!entries.isDirect) {
            throw IllegalArgumentException("If non-null, entries buffer must be a direct buffer")
        }
        GLES32.glBindBuffer(target, bufferId[0])
        GLError.maybeThrowGLException("Failed to bind vertex buffer object", "glBindBuffer")

        entries.rewind()

        if (entries.limit() <= capacity) {
            GLES32.glBufferSubData(target, 0, entries.limit() * numberOfBytesPerEntry, entries)
            GLError.maybeThrowGLException("Failed to populate vertex buffer object", "glBufferSubData")
            size = entries.limit()
        } else {
            GLES32.glBufferData(target, entries.limit() * numberOfBytesPerEntry, entries, GLES32.GL_DYNAMIC_DRAW)
            GLError.maybeThrowGLException("Failed to populate vertex buffer object", "glBufferData")
            size = entries.limit()
            capacity = entries.limit()
        }
    }

    fun free() {
        if (bufferId.first() != 0) {
            GLES32.glDeleteBuffers(1, bufferId, 0)
            GLError.maybeLogGLError(Log.WARN, TAG, "Failed to free buffer object", "glDeleteBuffers")
            bufferId.set(0,0)
        }
    }

    fun getBufferId(): Int = bufferId.first()

    fun getSize(): Int = size
}
