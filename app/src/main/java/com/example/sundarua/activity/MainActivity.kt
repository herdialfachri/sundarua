package com.example.sundarua.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.sundarua.databinding.ActivityMainBinding
import com.example.sundarua.test.UserPrefManager

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        // cek apakah nama pengguna sudah tersedia
        if (!UserPrefManager.isUserNameAvailable(this)) {
            redirectToIdentity()
            return
        }

        // view binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // pemanggilan fungsi
        setupGreeting()
        setupNavigation()
        updateCoinAndLevel()
    }

    override fun onResume() {
        super.onResume()

        // pemanggilan fungsi
        updateCoinAndLevel()
    }

    // fungsi untuk menampilkan nama pengguna
    private fun setupGreeting() {
        val name = UserPrefManager.getUserName(this) ?: return
        binding.greeting.text = UserPrefManager.getGreeting(name)
    }

    // fungsi untuk navigasi ke halaman lain
    private fun setupNavigation() {
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

    // fungsi untuk memperbarui coin dan level
    private fun updateCoinAndLevel() {
        val gamePref = getSharedPreferences("game_data", Context.MODE_PRIVATE)

        val coin = gamePref.getInt("coin", 0)
        val level = gamePref.getInt("level", 0)

        val (finalCoin, finalLevel) = UserPrefManager.getCoinAndLevel(mapOf(
            "coin" to coin,
            "level" to level
        ))

        binding.coinTv.text = "Coin: $finalCoin"
        binding.levelTv.text = "Level: $finalLevel"
    }

    // fungsi mengalihkan halaman ke halaman identity
    private fun redirectToIdentity() {
        startActivity(Intent(this, IdentityActivity::class.java))
        finish()
    }
}