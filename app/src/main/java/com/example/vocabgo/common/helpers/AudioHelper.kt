package com.example.vocabgo.common.helpers

import android.content.Context
import android.media.MediaPlayer

class AudioHelper {
    companion object {

        fun playAudioFromUrl(context: Context, url: String?) {
            val mediaPlayer = MediaPlayer().apply {
                setDataSource(url)
                setOnPreparedListener { start() }
                setOnCompletionListener {
                    it.release()
                }
                prepareAsync()
            }
        }

    }
}