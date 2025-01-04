package com.example.hw1

import android.os.Bundle
import android.widget.FrameLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.hw1.fragments.HighScoreFragment
import com.example.hw1.interfaces.Callback_HighScoreItemClicked
import com.example.hw1.fragments.MapFragment

class RecordsMapActivity : AppCompatActivity() {

    private lateinit var main_FRAME_list: FrameLayout

    private lateinit var main_FRAME_map: FrameLayout

    private lateinit var mapFragment: MapFragment

    private lateinit var highScoreFragment: HighScoreFragment


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
}