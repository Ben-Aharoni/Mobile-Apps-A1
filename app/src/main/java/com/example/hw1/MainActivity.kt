package com.example.hw1

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.textview.MaterialTextView

class MainActivity : AppCompatActivity() {

    private lateinit var main_LBL_score: MaterialTextView

    private lateinit var hearts: Array<AppCompatImageView>

    private lateinit var leftArrow: ExtendedFloatingActionButton

    private lateinit var rightArrow: ExtendedFloatingActionButton

    private lateinit var thief: Array<ImageView>

    private lateinit var cops: Array<Array<ImageView>>

    private lateinit var gameManager: GameManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViews()
        initGameManager()
        initViews()
    }

    private fun findViews() {

        main_LBL_score = findViewById(R.id.main_LBL_score)

        hearts = arrayOf(
            findViewById(R.id.main_IMG_heart0),
            findViewById(R.id.main_IMG_heart1),
            findViewById(R.id.main_IMG_heart2)
        )

        leftArrow = findViewById(R.id.main_FAB_LeftArrow)
        rightArrow = findViewById(R.id.main_FAB_RightArrow)

        thief = arrayOf(
            findViewById(R.id.main_IMG_thief_left),
            findViewById(R.id.main_IMG_thief_middle),
            findViewById(R.id.main_IMG_thief_right),
            findViewById(R.id.main_IMG_thief_right1),
            findViewById(R.id.main_IMG_thief_right2)
        )

        cops = arrayOf(
            arrayOf(
                findViewById(R.id.main_IMG_cop26),
                findViewById(R.id.main_IMG_cop27),
                findViewById(R.id.main_IMG_cop28),
                findViewById(R.id.main_IMG_cop29),
                findViewById(R.id.main_IMG_cop30)
            ), arrayOf(
                findViewById(R.id.main_IMG_cop1),
                findViewById(R.id.main_IMG_cop2),
                findViewById(R.id.main_IMG_cop3),
                findViewById(R.id.main_IMG_cop16),
                findViewById(R.id.main_IMG_cop21)
            ), arrayOf(
                findViewById(R.id.main_IMG_cop4),
                findViewById(R.id.main_IMG_cop5),
                findViewById(R.id.main_IMG_cop6),
                findViewById(R.id.main_IMG_cop17),
                findViewById(R.id.main_IMG_cop22)
            ), arrayOf(
                findViewById(R.id.main_IMG_cop7),
                findViewById(R.id.main_IMG_cop8),
                findViewById(R.id.main_IMG_cop9),
                findViewById(R.id.main_IMG_cop18),
                findViewById(R.id.main_IMG_cop23)
            ), arrayOf(
                findViewById(R.id.main_IMG_cop10),
                findViewById(R.id.main_IMG_cop11),
                findViewById(R.id.main_IMG_cop12),
                findViewById(R.id.main_IMG_cop19),
                findViewById(R.id.main_IMG_cop24)
            ), arrayOf(
                findViewById(R.id.main_IMG_cop13),
                findViewById(R.id.main_IMG_cop14),
                findViewById(R.id.main_IMG_cop15),
                findViewById(R.id.main_IMG_cop20),
                findViewById(R.id.main_IMG_cop25)
            )
        )
    }

    private fun initGameManager() {
        gameManager = GameManager(thief = thief,
            cops = cops,
            hearts = hearts,
            onGameOver = { resetGame() },
            onScoreUpdated = { newScore -> updateScoreUI(newScore) })
    }

    private fun updateScoreUI(newScore: Int) {
        runOnUiThread {
            main_LBL_score.text = newScore.toString()
        }
    }


    private fun initViews() {

        main_LBL_score.text = gameManager.score.toString()

        leftArrow.setOnClickListener {
            gameManager.moveThiefLeft()
        }

        rightArrow.setOnClickListener {
            gameManager.moveThiefRight()
        }

        gameManager.startGame()
    }

    private fun resetGame() {
        gameManager.resetGame()
    }
}

