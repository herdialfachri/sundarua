package com.example.sundarua.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
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
            showConfirmationDialog(200, "Buku")
        }

        getPencilBtn.setOnClickListener {
            showConfirmationDialog(100, "Pensil")
        }

        showClaimHistory()

        val backToMainBtn = findViewById<ImageView>(R.id.back_main_btn)
        backToMainBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun showConfirmationDialog(price: Int, itemName: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Tukerkeun Hadiah")
        builder.setMessage("Bade nukerkeun $itemName sareng $price koin?")
        builder.setPositiveButton("Leres") { dialog, _ ->
            claimReward(price, itemName)
            dialog.dismiss()
        }
        builder.setNegativeButton("Sanes") { dialog, _ ->
            dialog.dismiss()
        }
        builder.create().show()
    }

    private fun claimReward(price: Int, itemName: String) {
        val sharedPref = getSharedPreferences("game_data", Context.MODE_PRIVATE)
        val coin = sharedPref.getInt("coin", 0)

        if (coin >= price) {
            val newCoin = coin - price
            sharedPref.edit().putInt("coin", newCoin).apply()

            val code = generateUniqueCode()

            val rewardPref = getSharedPreferences("reward_data", Context.MODE_PRIVATE)
            val previousHistory = rewardPref.getStringSet("claim_history", setOf())?.toMutableSet() ?: mutableSetOf()
            previousHistory.add("$itemName - Kode: $code")

            rewardPref.edit().putStringSet("claim_history", previousHistory.toSet()).apply()

            Toast.makeText(this, "Hadiah $itemName atos dicandak!\nKode: $code", Toast.LENGTH_SHORT).show()

            showClaimHistory()
        } else {
            Toast.makeText(this, "Koin teu cukup $itemName!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun generateUniqueCode(): String {
        val timestamp = SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault()).format(Date())
        return "CLM$timestamp"
    }

    private fun showClaimHistory() {
        claimHistoryLayout.removeAllViews()

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
            noClaimText.text = "Teu acan aya hadiah"
            noClaimText.textSize = 16f
            claimHistoryLayout.addView(noClaimText)
        }
    }
}