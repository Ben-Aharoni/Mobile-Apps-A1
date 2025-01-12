package com.example.hw1

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.hw1.fragments.HighScoreFragment
import com.example.hw1.fragments.MapFragment
import com.example.hw1.interfaces.Callback_HighScoreItemClicked
import com.example.hw1.interfaces.LocationUpdatedCallback
import com.example.hw1.model.Score
import com.example.hw1.utilities.LocationDetector

class RecordsMapActivity : AppCompatActivity(), LocationUpdatedCallback {

    private lateinit var main_FRAME_list: FrameLayout

    private lateinit var main_FRAME_map: FrameLayout

    private lateinit var mapFragment: MapFragment

    private lateinit var highScoreFragment: HighScoreFragment

    private lateinit var locationDetector: LocationDetector

    private var currentScore: Int = 0

    private var currentLat: Double = 0.0

    private var currentLon: Double = 0.0

    private val LOCATION_PERMISSION_REQUEST_CODE = 1001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_records_map)

        findViews()
        initViews()
        locationDetector = LocationDetector(this, this)
        checkLocationPermission()
        loadScoreFromIntent()
    }

    private fun findViews() {
        main_FRAME_list = findViewById(R.id.main_FRAME_list)
        main_FRAME_map = findViewById(R.id.main_FRAME_map)
    }

    private fun initViews() {
        mapFragment = MapFragment()
        supportFragmentManager.beginTransaction()
            .add(R.id.main_FRAME_map, mapFragment)
            .commit()

        highScoreFragment = HighScoreFragment()

        highScoreFragment.highScoreItemClicked = object : Callback_HighScoreItemClicked {
            override fun highScoreItemClicked(lat: Double, lon: Double) {
                mapFragment.placeMarkerAndZoom(lat, lon, "Score Location")
            }
        }

        supportFragmentManager.beginTransaction()
            .add(R.id.main_FRAME_list, highScoreFragment)
            .commit()
    }

    override fun onResume() {
        super.onResume()
        loadScoreFromIntent()
        addScore()
        highScoreFragment.updateScore()
    }


    private fun loadScoreFromIntent() {
        val bundle = intent.extras ?: return
        currentScore = bundle.getInt("Score", 0)
        currentLat = bundle.getDouble("Lat", 0.0)
        currentLon = bundle.getDouble("Lon", 0.0)
        if (currentLat != 0.0 && currentLon != 0.0) {
            addScore()
        }
    }

    private fun addScore() {
        if (currentScore == 0) return

        val lat = currentLat
        val lon = currentLon
        val scores = ScoreManager.getInstance(this).scores
        if (scores.size < 10 || currentScore > scores.last().score) {
            val newScore = Score(currentScore, lat, lon)
            ScoreManager.getInstance(this).updateScore(newScore)
            highScoreFragment.updateScore()
        }
        currentScore = 0
    }

    override fun onLocationUpdated(latitude: Double, longitude: Double) {
        currentLat = latitude
        currentLon = longitude
    }

    private fun checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        } else {
            locationDetector.startLocationUpdates()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                locationDetector.startLocationUpdates()
            } else {
                Toast.makeText(
                    this,
                    "Location permission is required to show score locations on the map.",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        locationDetector.stopLocationUpdates()
    }
}
