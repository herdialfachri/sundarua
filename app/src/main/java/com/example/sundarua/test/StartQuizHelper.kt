package com.example.sundarua.test

import android.content.Context
import android.media.MediaPlayer
import androidx.core.content.edit

object StartQuizHelper {
    private var mediaPlayer: MediaPlayer? = null

    fun calculateScorePercentage(score: Int, total: Int): Int {
        return if (total == 0) 0 else ((score.toFloat() / total) * 100).toInt()
    }

    fun updateCoinAndLevel(currentCoin: Int, currentLevel: Int, isPassed: Boolean): Pair<Int, Int> {
        return if (isPassed) {
            Pair(currentCoin + 100, currentLevel + 1)
        } else {
            Pair(currentCoin, currentLevel)
        }
    }

    fun getCoin(context: Context): Int {
        val sharedPref = context.getSharedPreferences("game_data", Context.MODE_PRIVATE)
        return sharedPref.getInt("coin", 0)
    }

    fun getLevel(context: Context): Int {
        val sharedPref = context.getSharedPreferences("game_data", Context.MODE_PRIVATE)
        return sharedPref.getInt("level", 0)
    }

    fun saveCoinAndLevel(context: Context, coin: Int, level: Int) {
        val sharedPref = context.getSharedPreferences("game_data", Context.MODE_PRIVATE)
        sharedPref.edit() {
            putInt("coin", coin)
                .putInt("level", level)
        }
    }

    fun startBackgroundMusic(context: Context, resId: Int) {
        mediaPlayer = MediaPlayer.create(context, resId)
        mediaPlayer?.isLooping = true
        mediaPlayer?.start()
    }

    fun stopBackgroundMusic() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}