package com.example.sundarua.test

object QuizUtils {

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
}