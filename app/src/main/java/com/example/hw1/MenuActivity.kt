package com.example.hw1

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import com.google.android.material.button.MaterialButton
import com.google.android.material.materialswitch.MaterialSwitch
import com.google.android.material.textview.MaterialTextView

class MenuActivity : AppCompatActivity() {


    private lateinit var title_IMG_cop: AppCompatImageView

    private lateinit var title_IMG_thief: AppCompatImageView

    private lateinit var title_LBL_title: MaterialTextView

    private lateinit var title_BTN_start: MaterialButton

    private lateinit var title_BTN_records: MaterialButton

    private lateinit var title_SWITCH_Mode: MaterialSwitch

    private lateinit var title_SWITCH_speed: MaterialSwitch




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)


        findViews()
        initViews()

    }


    private fun findViews() {
        title_IMG_cop = findViewById(R.id.title_IMG_cop)
        title_IMG_thief = findViewById(R.id.title_IMG_thief)
        title_LBL_title = findViewById(R.id.title_LBL_title)
        title_BTN_start = findViewById(R.id.title_BTN_start)
        title_BTN_records = findViewById(R.id.title_BTN_records)
        title_SWITCH_Mode = findViewById(R.id.title_SWITCH_Mode)
        title_SWITCH_speed = findViewById(R.id.title_SWITCH_speed)
    }

    private fun initViews() {


        title_BTN_start.setOnClickListener {
            val isUsingSensors = title_SWITCH_Mode.isChecked
            //val gameSpeed = if (title_SWITCH_speed.isChecked) "FAST" else "SLOW"
            val intent = Intent(this, MainActivity::class.java).apply {
                putExtra("GAME_MODE", if (isUsingSensors) "SENSORS" else "BUTTONS")
               // putExtra("GAME_SPEED", gameSpeed)
                putExtra("IS_USING_SENSORS", isUsingSensors)
            }
            startActivity(intent)
        }

        title_SWITCH_Mode.setOnCheckedChangeListener { _, isChecked ->
            val isUsingSensors = title_SWITCH_Mode.isChecked
            Intent().apply {
                putExtra("IS_USING_SENSORS", isUsingSensors)

            }

            // Records Button Click Listener
            /* title_BTN_records.setOnClickListener {
                 val intent = Intent(this, RecordsActivity::class.java) // Assuming you have a RecordsActivity
                 startActivity(intent)
             }*/

            title_SWITCH_speed.setOnCheckedChangeListener { _, isChecked ->
                val speed = if (isChecked) "Fast Speed" else "Slow Speed"
                println("Game Speed Changed: $speed") // Or show a Toast
            }
        }


    }
}