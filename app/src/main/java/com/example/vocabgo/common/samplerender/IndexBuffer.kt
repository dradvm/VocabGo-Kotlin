package com.example.vocabgo.common.samplerender

import android.opengl.GLES32
import java.io.Closeable
import java.nio.IntBuffer

class IndexBuffer(render: SampleRender, entries: IntBuffer?) : Closeable {

    private val buffer: GpuBuffer = GpuBuffer(GLES32.GL_ELEMENT_ARRAY_BUFFER, GpuBuffer.INT_SIZE, entries)

    fun set(entries: IntBuffer?) {
        buffer.set(entries)
    }

    override fun close() {
        buffer.free()
    }

    internal fun getBufferId(): Int = buffer.getBufferId()

    internal fun getSize(): Int = buffer.getSize()
}
