package com.example.sundarua.test

import android.content.Context
import java.text.SimpleDateFormat
import java.util.*
import androidx.core.content.edit

class RewardHelper {

    fun canClaimReward(currentCoin: Int, price: Int): Boolean {
        return currentCoin >= price
    }

    fun generateClaimCode(): String {
        val timestamp = SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault()).format(Date())
        return "CLM$timestamp"
    }

    fun formatClaimHistory(itemName: String, code: String): String {
        return "$itemName - Kode: $code"
    }

    fun getCurrentCoin(context: Context): Int {
        val sharedPref = context.getSharedPreferences("game_data", Context.MODE_PRIVATE)
        return sharedPref.getInt("coin", 0)
    }

    fun updateCoin(context: Context, newCoin: Int) {
        val sharedPref = context.getSharedPreferences("game_data", Context.MODE_PRIVATE)
        sharedPref.edit() { putInt("coin", newCoin) }
    }

    fun saveClaimHistory(context: Context, itemName: String) {
        val rewardPref = context.getSharedPreferences("reward_data", Context.MODE_PRIVATE)
        val previousHistory = rewardPref.getStringSet("claim_history", setOf())?.toMutableSet() ?: mutableSetOf()

        val code = generateClaimCode()
        val newEntry = formatClaimHistory(itemName, code)
        previousHistory.add(newEntry)

        rewardPref.edit() { putStringSet("claim_history", previousHistory) }
    }

    fun getClaimHistory(context: Context): Set<String> {
        val rewardPref = context.getSharedPreferences("reward_data", Context.MODE_PRIVATE)
        return rewardPref.getStringSet("claim_history", setOf()) ?: setOf()
    }
}