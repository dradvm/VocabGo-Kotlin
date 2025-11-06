package com.example.vocabgo.common.samplerender

import android.opengl.GLException


import android.opengl.GLES30
import android.opengl.GLU
import android.util.Log
import java.util.*

object GLError {

    /** Throws a [GLException] if a GL error occurred. */
    @JvmStatic
    fun maybeThrowGLException(reason: String, api: String) {
        val errorCodes = getGlErrors()
        if (!errorCodes.isNullOrEmpty()) {
            throw GLException(
                errorCodes[0],
                formatErrorMessage(reason, api, errorCodes)
            )
        }
    }

    /** Logs a message with the given logcat priority if a GL error occurred. */
    @JvmStatic
    fun maybeLogGLError(priority: Int, tag: String, reason: String, api: String) {
        val errorCodes = getGlErrors()
        if (!errorCodes.isNullOrEmpty()) {
            Log.println(priority, tag, formatErrorMessage(reason, api, errorCodes))
        }
    }

    private fun formatErrorMessage(reason: String, api: String, errorCodes: List<Int>): String {
        return buildString {
            append("$reason: $api: ")
            errorCodes.forEachIndexed { index, errorCode ->
                append("${GLU.gluErrorString(errorCode)} ($errorCode)")
                if (index < errorCodes.size - 1) append(", ")
            }
        }
    }

    private fun getGlErrors(): List<Int>? {
        var errorCode = GLES30.glGetError()
        if (errorCode == GLES30.GL_NO_ERROR) return null

        val errorCodes = mutableListOf<Int>()
        errorCodes.add(errorCode)

        while (true) {
            errorCode = GLES30.glGetError()
            if (errorCode == GLES30.GL_NO_ERROR) break
            errorCodes.add(errorCode)
        }

        return errorCodes
    }
}
