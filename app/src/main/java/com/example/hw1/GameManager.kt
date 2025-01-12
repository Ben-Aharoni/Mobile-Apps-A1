package com.example.hw1

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import com.example.hw1.utilities.SignalManager
import com.example.hw1.utilities.SingleSoundPlayer
import kotlin.random.Random

class GameManager(
    context: Context,
    private val thief: Array<ImageView>,
    private val cops: Array<Array<ImageView>>,
    private val coins: Array<Array<ImageView>>,
    private val hearts: Array<AppCompatImageView>,
    private val onGameOver: () -> Unit,
    private val onScoreUpdated: (Int) -> Unit
) {

    private val singleSoundPlayer = SingleSoundPlayer(context)

    var score: Int = 0
        private set

    private var thiefPosition = 2

    private var lives = 3

    private var gameOver = false

    val handler = Handler(Looper.getMainLooper())

    private var gameSpeed: Long = 1000L


    fun updateSpeed(isFast: Boolean) {
        gameSpeed = if (isFast) 500L else 1000L
    }

    fun resetGame() {
        lives = 3
        gameOver = false

        resetHearts()
        resetScore()
        resetThief()
        resetCops()
        resetCoins()
        placeCopsRandomly()
        placeCoinsRandomly()
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

    private fun resetCoins() {
        for (row in coins) {
            for (coin in row) {
                coin.visibility = View.INVISIBLE
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

    private fun placeCoinsRandomly() {
        val firstRowCoins = coins[0]
        val firstRowCops = cops[0]
        for (i in firstRowCoins.indices) {
            firstRowCoins[i].visibility = View.INVISIBLE
        }
        var randomIndex: Int
        do {
            randomIndex = Random.nextInt(firstRowCoins.size)
        } while (firstRowCops[randomIndex].visibility == View.VISIBLE)
        firstRowCoins[randomIndex].visibility = View.VISIBLE
    }


    fun moveThiefLeft() {
        if (!gameOver && thiefPosition > 0) {
            thief[thiefPosition].visibility = View.INVISIBLE
            thiefPosition--
            thief[thiefPosition].visibility = View.VISIBLE
        }
    }


    fun moveThiefRight() {
        if (!gameOver && thiefPosition < 4) {
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
                    moveCoinsDown()
                    placeCopsRandomly()
                    placeCoinsRandomly()
                    checkCollisions()
                    handler.postDelayed(this, gameSpeed)
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

    private fun moveCoinsDown() {
        for (row in coins.indices.reversed()) {
            for (col in coins[row].indices) {
                val coin = coins[row][col]
                if (coin.visibility == View.VISIBLE) {
                    coin.visibility = View.INVISIBLE
                    if (row + 1 < coins.size) {
                        coins[row + 1][col].visibility = View.VISIBLE
                    }
                }
            }
        }
    }


    private fun checkCollisions() {
        if (cops.last()[thiefPosition].visibility == View.VISIBLE) {
            handleCollision()
        }
        if (coins.last()[thiefPosition].visibility == View.VISIBLE) {
            coinCollected()
        }
    }


    private fun handleCollision() {
        //singleSoundPlayer.playSound(R.raw.boom_sound)
        lives--
        updateScore(-50)
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
            SignalManager.getInstance().toast("Collision! -50")
            SignalManager.getInstance().vibrate()
        }
    }

    private fun coinCollected() {
        // singleSoundPlayer.playSound(R.raw.coin_sound)
        updateScore(100)
        SignalManager.getInstance().toast("Coin Collected! +100")
        SignalManager.getInstance().vibrate()

    }

    private fun updateScore(points: Int) {
        score += points
        if (score < 0) {
            score = 0
        }
        onScoreUpdated(score)
    }




}



