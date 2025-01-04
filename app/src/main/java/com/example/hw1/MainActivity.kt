package com.example.hw1

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import com.example.hw1.interfaces.TiltCallback
import com.example.hw1.utilities.SingleSoundPlayer
import com.example.hw1.utilities.TiltDetector
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.textview.MaterialTextView

class MainActivity : AppCompatActivity() {


    private lateinit var main_LBL_score: MaterialTextView

    private lateinit var hearts: Array<AppCompatImageView>

    private lateinit var leftArrow: ExtendedFloatingActionButton

    private lateinit var rightArrow: ExtendedFloatingActionButton

    private lateinit var thief: Array<ImageView>

    private lateinit var cops: Array<Array<ImageView>>

    private lateinit var coins: Array<Array<ImageView>>

    private lateinit var gameManager: GameManager

    private lateinit var tiltDetector: TiltDetector

    private var isTiltMode = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        isTiltMode = intent.getBooleanExtra("IS_TILT_MODE", false)
        val isFastSpeed = intent.getBooleanExtra("GAME_SPEED", false)
        findViews()
        initGameManager()
        initViews()
        if (isTiltMode) {
            initTiltDetector()
            leftArrow.visibility = View.GONE
            rightArrow.visibility = View.GONE
        }
        gameManager.updateSpeed(isFastSpeed)
        gameManager.startGame()
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
            findViewById(R.id.main_IMG_thief_1),
            findViewById(R.id.main_IMG_thief_2),
            findViewById(R.id.main_IMG_thief_3),
            findViewById(R.id.main_IMG_thief_4),
            findViewById(R.id.main_IMG_thief_5)
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
            ), arrayOf(
                findViewById(R.id.main_IMG_cop31),
                findViewById(R.id.main_IMG_cop32),
                findViewById(R.id.main_IMG_cop33),
                findViewById(R.id.main_IMG_cop34),
                findViewById(R.id.main_IMG_cop35)
            ), arrayOf(
                findViewById(R.id.main_IMG_cop36),
                findViewById(R.id.main_IMG_cop37),
                findViewById(R.id.main_IMG_cop38),
                findViewById(R.id.main_IMG_cop39),
                findViewById(R.id.main_IMG_cop40)
            )
        )

        coins = arrayOf(
            arrayOf(
                findViewById(R.id.main_IMG_coin1),
                findViewById(R.id.main_IMG_coin2),
                findViewById(R.id.main_IMG_coin3),
                findViewById(R.id.main_IMG_coin4),
                findViewById(R.id.main_IMG_coin5)
            ), arrayOf(
                findViewById(R.id.main_IMG_coin6),
                findViewById(R.id.main_IMG_coin7),
                findViewById(R.id.main_IMG_coin8),
                findViewById(R.id.main_IMG_coin9),
                findViewById(R.id.main_IMG_coin10)
            ), arrayOf(
                findViewById(R.id.main_IMG_coin11),
                findViewById(R.id.main_IMG_coin12),
                findViewById(R.id.main_IMG_coin13),
                findViewById(R.id.main_IMG_coin14),
                findViewById(R.id.main_IMG_coin15)
            ), arrayOf(
                findViewById(R.id.main_IMG_coin16),
                findViewById(R.id.main_IMG_coin17),
                findViewById(R.id.main_IMG_coin18),
                findViewById(R.id.main_IMG_coin19),
                findViewById(R.id.main_IMG_coin20)
            ), arrayOf(
                findViewById(R.id.main_IMG_coin21),
                findViewById(R.id.main_IMG_coin22),
                findViewById(R.id.main_IMG_coin23),
                findViewById(R.id.main_IMG_coin24),
                findViewById(R.id.main_IMG_coin25)
            ), arrayOf(
                findViewById(R.id.main_IMG_coin26),
                findViewById(R.id.main_IMG_coin27),
                findViewById(R.id.main_IMG_coin28),
                findViewById(R.id.main_IMG_coin29),
                findViewById(R.id.main_IMG_coin30)
            ), arrayOf(
                findViewById(R.id.main_IMG_coin31),
                findViewById(R.id.main_IMG_coin32),
                findViewById(R.id.main_IMG_coin33),
                findViewById(R.id.main_IMG_coin34),
                findViewById(R.id.main_IMG_coin35)
            ), arrayOf(
                findViewById(R.id.main_IMG_coin36),
                findViewById(R.id.main_IMG_coin37),
                findViewById(R.id.main_IMG_coin38),
                findViewById(R.id.main_IMG_coin39),
                findViewById(R.id.main_IMG_coin40)
            )
        )
    }

    private fun initGameManager() {
        gameManager = GameManager(
            context = this,
            thief = thief,
            cops = cops,
            coins = coins,
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

        if (!isTiltMode) {
            leftArrow.setOnClickListener {
                gameManager.moveThiefLeft()
            }
            rightArrow.setOnClickListener {
                gameManager.moveThiefRight()
            }
        }

    }

    private fun resetGame() {
        gameManager.resetGame()
    }

    private fun initTiltDetector() {
        tiltDetector = TiltDetector(
            context = this,
            tiltCallback = object : TiltCallback {
                override fun tiltX() {
                    if (tiltDetector.tiltCounterX > 0) {
                        gameManager.moveThiefRight()
                    } else {
                        gameManager.moveThiefLeft()
                    }
                }
            }
        )
        tiltDetector.start()
    }

    override fun onResume() {
        super.onResume()
        if (isTiltMode) {
            initTiltDetector()
            tiltDetector.start()
        }
    }

    override fun onStop() {
        super.onStop()
        if (isTiltMode) {
            tiltDetector.stop()
        }
    }

    override fun onPause() {
        super.onPause()
        tiltDetector.stop()
    }

    override fun onDestroy() {
        super.onDestroy()
        tiltDetector.stop()
    }


}

