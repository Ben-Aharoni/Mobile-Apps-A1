package com.example.hw1

import android.os.Handler
import android.view.View
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import com.example.hw1.utilities.SignalManager
import com.google.android.material.textview.MaterialTextView
import kotlin.random.Random

class GameManager(
    private val thief: Array<ImageView>,
    private val cops: Array<Array<ImageView>>,
    private val hearts: Array<AppCompatImageView>,
    private val onGameOver: () -> Unit,
    private val onScoreUpdated: (Int) -> Unit
) {
    var score: Int = 0
        private set

    private var thiefPosition = 1

    private var lives = 3

    private var gameOver = false

    private val handler = Handler()

    private lateinit var main_LBL_score: MaterialTextView


    fun resetGame() {
        lives = 3
        gameOver = false

        resetHearts()
        resetScore()
        resetThief()
        resetCops()
        placeCopsRandomly()
    }


    private fun resetThief() {
        for (i in thief.indices) {
            thief[i].visibility = View.INVISIBLE
        }
        thief[thiefPosition].visibility = View.VISIBLE
    }


    private fun resetHearts() {
        for (heart in hearts) {
            heart.visibility = View.VISIBLE
        }
    }


    private fun resetCops() {
        for (row in cops) {
            for (cop in row) {
                cop.visibility = View.INVISIBLE
            }
        }
    }

    private fun resetScore() {
        score = 0
        onScoreUpdated(score)
    }


    private fun placeCopsRandomly() {
        val firstRow = cops[0]
        for (i in firstRow.indices) {
            firstRow[i].visibility = View.INVISIBLE
        }
        val randomIndex = Random.nextInt(firstRow.size)
        firstRow[randomIndex].visibility = View.VISIBLE
    }


    fun moveThiefLeft() {
        if (!gameOver && thiefPosition > 0) {
            thief[thiefPosition].visibility = View.INVISIBLE
            thiefPosition--
            thief[thiefPosition].visibility = View.VISIBLE
        }
    }


    fun moveThiefRight() {
        if (!gameOver && thiefPosition < 2) {
            thief[thiefPosition].visibility = View.INVISIBLE
            thiefPosition++
            thief[thiefPosition].visibility = View.VISIBLE
        }
    }


    fun startGame() {
        resetGame()
        val gameLoopRunnable = object : Runnable {
            override fun run() {
                if (!gameOver) {
                    moveCopsDown()
                    placeCopsRandomly()
                    checkCollisions()
                    updateScore(10)
                    handler.postDelayed(this, 1000)
                }
            }
        }
        handler.post(gameLoopRunnable)
    }


    private fun moveCopsDown() {
        for (row in cops.indices.reversed()) {
            for (col in cops[row].indices) {
                val cop = cops[row][col]
                if (cop.visibility == View.VISIBLE) {
                    cop.visibility = View.INVISIBLE
                    if (row + 1 < cops.size) {
                        cops[row + 1][col].visibility = View.VISIBLE
                    }
                }
            }
        }
    }


    private fun checkCollisions() {
        if (cops.last()[thiefPosition].visibility == View.VISIBLE) {
            handleCollision()
        }
    }


    private fun handleCollision() {
        lives--
        updateScore(-20)
        toastAndVibrate()
        hearts[lives].visibility = View.INVISIBLE
        if (lives == 0) {
            gameOver = true
            onGameOver()
        }
    }


    private fun toastAndVibrate() {
        if (lives == 0) {
            SignalManager.getInstance().toast("Game Over your score is : " + score)
            SignalManager.getInstance().vibrate()
        } else {
            SignalManager.getInstance().toast("collision!")
            SignalManager.getInstance().vibrate()
        }
    }

    private fun updateScore(points: Int) {
        score += points
        if (score < 0) {
            score = 0
        }
        onScoreUpdated(score)
    }
}



