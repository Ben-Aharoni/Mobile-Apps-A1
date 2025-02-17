package com.example.hw1

import android.app.Application
import com.example.hw1.utilities.SignalManager

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        SignalManager.init(this)
        ScoreManager.getInstance(this)
        ScoreManager.getInstance(this).loadScores()

    }
}