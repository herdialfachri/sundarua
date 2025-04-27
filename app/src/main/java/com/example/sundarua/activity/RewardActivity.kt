package com.example.sundarua.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.sundarua.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class RewardActivity : AppCompatActivity() {

    private lateinit var getBookBtn: Button
    private lateinit var getPencilBtn: Button
    private lateinit var claimHistoryLayout: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reward)

        getBookBtn = findViewById(R.id.getBookBtn)
        getPencilBtn = findViewById(R.id.getPencilBtn)
        claimHistoryLayout = findViewById(R.id.claimHistoryLayout)

        getBookBtn.setOnClickListener {
            claimReward(200, "Buku")
        }

        getPencilBtn.setOnClickListener {
            claimReward(100, "Pensil")
        }

        showClaimHistory()

        val backToMainBtn = findViewById<ImageView>(R.id.back_main_btn)

        backToMainBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun claimReward(price: Int, itemName: String) {
        val sharedPref = getSharedPreferences("game_data", Context.MODE_PRIVATE)
        val coin = sharedPref.getInt("coin", 0)

        if (coin >= price) {
            val newCoin = coin - price
            sharedPref.edit().putInt("coin", newCoin).apply()

            // Generate kode unik
            val code = generateUniqueCode()

            // Simpan riwayat claim
            val rewardPref = getSharedPreferences("reward_data", Context.MODE_PRIVATE)
            val previousHistory = rewardPref.getStringSet("claim_history", mutableSetOf()) ?: mutableSetOf()
            previousHistory.add("$itemName - Kode: $code")
            rewardPref.edit().putStringSet("claim_history", previousHistory).apply()

            Toast.makeText(this, "Berhasil mengambil $itemName!\nKode: $code", Toast.LENGTH_SHORT).show()

            // Refresh tampilkan riwayat terbaru
            showClaimHistory()
        } else {
            Toast.makeText(this, "Coin tidak cukup untuk membeli $itemName!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun generateUniqueCode(): String {
        val timestamp = SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault()).format(Date())
        return "CLM$timestamp"
    }

    private fun showClaimHistory() {
        claimHistoryLayout.removeAllViews() // Clear riwayat sebelumnya

        val rewardPref = getSharedPreferences("reward_data", Context.MODE_PRIVATE)
        val claimHistory = rewardPref.getStringSet("claim_history", setOf()) ?: setOf()

        if (claimHistory.isNotEmpty()) {
            for (claim in claimHistory) {
                val textView = TextView(this)
                textView.text = claim
                textView.textSize = 16f
                claimHistoryLayout.addView(textView)
            }
        } else {
            val noClaimText = TextView(this)
            noClaimText.text = "Belum ada hadiah yang di-claim."
            noClaimText.textSize = 16f
            claimHistoryLayout.addView(noClaimText)
        }
    }
}