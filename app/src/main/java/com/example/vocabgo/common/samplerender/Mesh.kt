package com.example.vocabgo.common.samplerender

import android.opengl.GLES32
import android.util.Log
import de.javagl.obj.ObjData
import de.javagl.obj.ObjReader
import de.javagl.obj.ObjUtils
import java.io.Closeable

class Mesh(
    render: SampleRender,
    private val primitiveMode: PrimitiveMode,
    private val indexBuffer: IndexBuffer?,
    private val vertexBuffers: Array<VertexBuffer>
) : Closeable {

    private val vertexArrayId = IntArray(1)

    enum class PrimitiveMode(val glesEnum: Int) {
        POINTS(GLES32.GL_POINTS),
        LINE_STRIP(GLES32.GL_LINE_STRIP),
        LINE_LOOP(GLES32.GL_LINE_LOOP),
        LINES(GLES32.GL_LINES),
        TRIANGLE_STRIP(GLES32.GL_TRIANGLE_STRIP),
        TRIANGLE_FAN(GLES32.GL_TRIANGLE_FAN),
        TRIANGLES(GLES32.GL_TRIANGLES)
    }

    init {
        require(vertexBuffers.isNotEmpty()) { "Must pass at least one vertex buffer" }

        try {
            GLES32.glGenVertexArrays(1, vertexArrayId, 0)
            GLError.maybeThrowGLException("Failed to generate a vertex array", "glGenVertexArrays")

            GLES32.glBindVertexArray(vertexArrayId[0])
            GLError.maybeThrowGLException("Failed to bind vertex array object", "glBindVertexArray")

            indexBuffer?.let {
                GLES32.glBindBuffer(GLES32.GL_ELEMENT_ARRAY_BUFFER, it.getBufferId())
            }

            vertexBuffers.forEachIndexed { i, vb ->
                GLES32.glBindBuffer(GLES32.GL_ARRAY_BUFFER, vb.getBufferId())
                GLError.maybeThrowGLException("Failed to bind vertex buffer", "glBindBuffer")
                GLES32.glVertexAttribPointer(i, vb.getNumberOfEntriesPerVertex(), GLES32.GL_FLOAT, false, 0, 0)
                GLError.maybeThrowGLException("Failed to associate vertex buffer with vertex array", "glVertexAttribPointer")
                GLES32.glEnableVertexAttribArray(i)
                GLError.maybeThrowGLException("Failed to enable vertex buffer", "glEnableVertexAttribArray")
            }
        } catch (t: Throwable) {
            close()
            throw t
        }
    }

    companion object {
        fun createFromAsset(render: SampleRender, assetFileName: String): Mesh {
            render.assetManager.open(assetFileName).use { inputStream ->
                val obj = ObjUtils.convertToRenderable(ObjReader.read(inputStream))

                val vertexIndices = ObjData.getFaceVertexIndices(obj, 3)
                val localCoordinates = ObjData.getVertices(obj)
                val textureCoordinates = ObjData.getTexCoords(obj, 2)
                val normals = ObjData.getNormals(obj)

                val vertexBuffers = arrayOf(
                    VertexBuffer(render, 3, localCoordinates),
                    VertexBuffer(render, 2, textureCoordinates),
                    VertexBuffer(render, 3, normals)
                )

                val indexBuffer = IndexBuffer(render, vertexIndices)

                return Mesh(render, PrimitiveMode.TRIANGLES, indexBuffer, vertexBuffers)
            }
        }
    }

    override fun close() {
        if (vertexArrayId[0] != 0) {
            GLES32.glDeleteVertexArrays(1, vertexArrayId, 0)
            GLError.maybeLogGLError(Log.WARN, "Mesh", "Failed to free vertex array object", "glDeleteVertexArrays")
        }
    }

    fun lowLevelDraw() {
        if (vertexArrayId[0] == 0) throw IllegalStateException("Tried to draw a freed Mesh")

        GLES32.glBindVertexArray(vertexArrayId[0])
        GLError.maybeThrowGLException("Failed to bind vertex array object", "glBindVertexArray")

        if (indexBuffer == null) {
            val vertexCount = vertexBuffers[0].getNumberOfVertices()
            vertexBuffers.drop(1).forEachIndexed { i, vb ->
                val iterCount = vb.getNumberOfVertices()
                if (iterCount != vertexCount) {
                    throw IllegalStateException(
                        "Vertex buffers have mismatching numbers of vertices ([0] has $vertexCount but [${i + 1}] has $iterCount)"
                    )
                }
            }
            GLES32.glDrawArrays(primitiveMode.glesEnum, 0, vertexCount)
            GLError.maybeThrowGLException("Failed to draw vertex array object", "glDrawArrays")
        } else {
            GLES32.glDrawElements(primitiveMode.glesEnum, indexBuffer.getSize(), GLES32.GL_UNSIGNED_INT, 0)
            GLError.maybeThrowGLException("Failed to draw vertex array object with indices", "glDrawElements")
        }
    }
}
