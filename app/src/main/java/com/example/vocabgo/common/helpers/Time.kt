package com.example.vocabgo.common.helpers

object Time {
    fun format(seconds: Int, showLeadingMinuteZero: Boolean = false): String {
        val minutes = seconds / 60
        val secs = seconds % 60
        return if (showLeadingMinuteZero)
            String.format("%02d:%02d", minutes, secs)
        else
            String.format("%d:%02d", minutes, secs)
    }
}