package com.example.sundarua.helper

object GameDataManager {
    fun getGreeting(name: String): String {
        return "Sampurasun, $name!"
    }

    fun getCoinAndLevel(pref: Map<String, Int>): Pair<Int, Int> {
        val coin = pref["coin"] ?: 0
        val level = pref["level"] ?: 0
        return Pair(coin, level)
    }
}