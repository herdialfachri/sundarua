package com.example.sundarua.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.sundarua.databinding.ActivityRewardBinding
import com.example.sundarua.helper.RewardHelper
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private val helper = RewardHelper()

class RewardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRewardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRewardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.getBookBtn.setOnClickListener {
            showConfirmationDialog(200, "Buku")
        }

        binding.getPencilBtn.setOnClickListener {
            showConfirmationDialog(100, "Pensil")
        }

        showClaimHistory()

        binding.backMainBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            startActivity(intent)
            finish()
        }
    }

    private fun showConfirmationDialog(price: Int, itemName: String) {
        AlertDialog.Builder(this)
            .setTitle("Tukerkeun Hadiah")
            .setMessage("Bade nukerkeun $itemName sareng $price koin?")
            .setPositiveButton("Leres") { dialog, _ ->
                claimReward(price, itemName)
                dialog.dismiss()
            }
            .setNegativeButton("Sanes") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }

    private fun claimReward(price: Int, itemName: String) {
        val sharedPref = getSharedPreferences("game_data", Context.MODE_PRIVATE)
        val coin = sharedPref.getInt("coin", 0)

        if (coin >= price) {
            val newCoin = coin - price
            sharedPref.edit().putInt("coin", newCoin).apply()

            val code = helper.generateClaimCode()

            val rewardPref = getSharedPreferences("reward_data", Context.MODE_PRIVATE)
            val previousHistory = rewardPref.getStringSet("claim_history", setOf())?.toMutableSet() ?: mutableSetOf()
            previousHistory.add(helper.formatClaimHistory(itemName, code))
            rewardPref.edit().putStringSet("claim_history", previousHistory).apply()

            Toast.makeText(this, "Hadiah $itemName atos dicandak!\nKode: $code", Toast.LENGTH_SHORT).show()

            showClaimHistory()
        } else {
            Toast.makeText(this, "Koin kanggo nukerkeun $itemName te cukup!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showClaimHistory() {
        binding.claimHistoryLayout.removeAllViews()

        val rewardPref = getSharedPreferences("reward_data", Context.MODE_PRIVATE)
        val claimHistory = rewardPref.getStringSet("claim_history", setOf()) ?: setOf()

        if (claimHistory.isNotEmpty()) {
            for (claim in claimHistory) {
                val textView = TextView(this)
                textView.text = claim
                textView.textSize = 16f
                binding.claimHistoryLayout.addView(textView)
            }
        } else {
            val noClaimText = TextView(this)
            noClaimText.text = "Teu acan aya hadiah"
            noClaimText.textSize = 16f
            binding.claimHistoryLayout.addView(noClaimText)
        }
    }
}