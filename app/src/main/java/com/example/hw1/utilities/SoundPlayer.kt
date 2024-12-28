package com.example.hw1.utilities

import android.content.Context
import android.media.MediaPlayer

class SoundPlayer(private val context: Context) {

    private var mediaPlayer: MediaPlayer? = null

    fun playSound(resId: Int) {
        release()
        mediaPlayer = MediaPlayer.create(context, resId).apply {
            setOnCompletionListener {
                release()
            }
            start()
        }
    }

    fun release() {
        mediaPlayer?.release()
        mediaPlayer = null
    }
}
