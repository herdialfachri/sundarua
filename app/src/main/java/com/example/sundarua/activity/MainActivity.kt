package com.example.sundarua.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.sundarua.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Thread.sleep(2500)
        installSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Cek apakah user sudah mengisi identity
        val sharedPref = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val name = sharedPref.getString("user_name", null)

        if (name == null) {
            startActivity(Intent(this, IdentityActivity::class.java))
            finish()
        } else {
            // Set nama pengguna
            binding.greeting.text = "Sampurasun, $name!"

            // Set coin dan level
            updateCoinAndLevel()

            // Tombol navigasi
            binding.toWordBtn.setOnClickListener {
                startActivity(Intent(this, WordActivity::class.java))
            }

            binding.toQuizBtn.setOnClickListener {
                startActivity(Intent(this, QuizActivity::class.java))
            }

            binding.toAksaraBtn.setOnClickListener {
                startActivity(Intent(this, AksaraActivity::class.java))
            }

            binding.coinTv.setOnClickListener {
                startActivity(Intent(this, RewardActivity::class.java))
            }
        }
    }

    override fun onResume() {
        super.onResume()
        updateCoinAndLevel() // Refresh coin dan level setiap kembali ke MainActivity
    }

    private fun updateCoinAndLevel() {
        val gamePref = getSharedPreferences("game_data", Context.MODE_PRIVATE)
        val coin = gamePref.getInt("coin", 0)
        val level = gamePref.getInt("level", 1)

        binding.coinTv.text = "Coin: $coin"
        binding.levelTv.text = "Level: $level"
    }
}