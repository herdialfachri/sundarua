package com.example.sundarua.helper

import java.text.SimpleDateFormat
import java.util.*

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
}