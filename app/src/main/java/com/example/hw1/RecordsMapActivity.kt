package com.example.hw1

import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.hw1.fragments.HighScoreFragment
import com.example.hw1.interfaces.Callback_HighScoreItemClicked
import com.example.hw1.fragments.MapFragment
import com.example.hw1.model.Score

class RecordsMapActivity : AppCompatActivity() {

    private lateinit var main_FRAME_list: FrameLayout

    private lateinit var main_FRAME_map: FrameLayout

    private lateinit var mapFragment: MapFragment

    private lateinit var highScoreFragment: HighScoreFragment

    private var currentScore: Int = 0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_records_map)

        findViews()
        initViews()
    }

    private fun findViews() {
        main_FRAME_list = findViewById(R.id.main_FRAME_list)
        main_FRAME_map = findViewById(R.id.main_FRAME_map)
    }

    private fun initViews() {
        mapFragment = MapFragment()

        supportFragmentManager
            .beginTransaction()
            .add(R.id.main_FRAME_map ,mapFragment )
            .commit()

        highScoreFragment = HighScoreFragment()
        highScoreFragment.highScoreItemClicked = object : Callback_HighScoreItemClicked {
            override fun highScoreItemClicked(lat: Double, lon: Double) {
                mapFragment.zoom(lat, lon)
            }
        }

        supportFragmentManager
            .beginTransaction()
            .add(R.id.main_FRAME_list ,highScoreFragment )
            .commit()
    }

    override fun onResume() {
        super.onResume()
        loadScoreFromIntent()
        var lat = intent.getDoubleExtra("lat", 0.0)
        var lang = intent.getDoubleExtra("lang", 0.0)
        addScore(lang,lat)
        val fragment = supportFragmentManager.findFragmentById(R.id.main_FRAME_list) as? HighScoreFragment
        fragment?.updateScore()
    }

    private fun loadScoreFromIntent() {
        val bundle = intent.extras ?: return
        currentScore = bundle.getInt("Score:", 0)
    }

    private fun addScore(lat: Double, lang: Double) {
        if (currentScore == 0)
            return

        val scores = ScoreManager.getInstance(this).scores
        if (scores.size < 10 || currentScore > scores.last().score) {
            val newScore = Score(currentScore, lat, lang)
            ScoreManager.getInstance(this).updateScore(newScore)
            highScoreFragment.updateScore()

        }
        currentScore = 0
    }

}