package com.example.sundarua.activity

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.sundarua.databinding.ActivityRewardBinding
import com.example.sundarua.test.RewardHelper

class RewardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRewardBinding
    private val helper = RewardHelper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRewardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRewardButtons()
        setupBackButton()
        showClaimHistory()
    }

    private fun setupRewardButtons() {
        binding.getBookBtn.setOnClickListener {
            showConfirmationDialog(price = 200, itemName = "Buku")
        }

        binding.getPencilBtn.setOnClickListener {
            showConfirmationDialog(price = 100, itemName = "Pensil")
        }
    }

    private fun setupBackButton() {
        binding.backMainBtn.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
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
        val currentCoin = helper.getCurrentCoin(this)

        if (helper.canClaimReward(currentCoin, price)) {
            helper.updateCoin(this, currentCoin - price)
            helper.saveClaimHistory(this, itemName)
            showSuccessToast(itemName)
            showClaimHistory()
        } else {
            showInsufficientCoinToast(itemName)
        }
    }

    private fun showClaimHistory() {
        binding.claimHistoryLayout.removeAllViews()

        val claimHistory = helper.getClaimHistory(this)
        val entries = if (claimHistory.isEmpty()) setOf("Teu acan aya hadiah") else claimHistory

        for (entry in entries) {
            val textView = TextView(this).apply {
                text = entry
                textSize = 16f
            }
            binding.claimHistoryLayout.addView(textView)
        }
    }

    private fun showSuccessToast(itemName: String) {
        Toast.makeText(this, "Hadiah $itemName atos dicandak!", Toast.LENGTH_SHORT).show()
    }

    private fun showInsufficientCoinToast(itemName: String) {
        Toast.makeText(this, "Koin kanggo nukerkeun $itemName te cukup!", Toast.LENGTH_SHORT).show()
    }
}