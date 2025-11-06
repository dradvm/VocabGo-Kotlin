package com.example.vocabgo.common.samplerender

import android.opengl.GLES32
import java.io.Closeable
import java.nio.FloatBuffer

class VertexBuffer(
    render: SampleRender,
    private val numberOfEntriesPerVertex: Int,
    entries: FloatBuffer?
) : Closeable {

    private val buffer: GpuBuffer = GpuBuffer(GLES32.GL_ARRAY_BUFFER, GpuBuffer.FLOAT_SIZE, entries?.apply {
        if (limit() % numberOfEntriesPerVertex != 0) {
            throw IllegalArgumentException(
                "If non-null, vertex buffer data must be divisible by the number of data points per vertex"
            )
        }
    })

    fun set(entries: FloatBuffer?) {
        if (entries != null && entries.limit() % numberOfEntriesPerVertex != 0) {
            throw IllegalArgumentException(
                "If non-null, vertex buffer data must be divisible by the number of data points per vertex"
            )
        }
        buffer.set(entries)
    }

    override fun close() {
        buffer.free()
    }

    internal fun getBufferId(): Int = buffer.getBufferId()

    internal fun getNumberOfEntriesPerVertex(): Int = numberOfEntriesPerVertex

    internal fun getNumberOfVertices(): Int = buffer.getSize() / numberOfEntriesPerVertex
}
