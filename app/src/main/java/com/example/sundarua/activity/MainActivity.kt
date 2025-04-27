package com.example.sundarua.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.sundarua.R

class MainActivity : AppCompatActivity() {

    private lateinit var greetingTextView: TextView
    private lateinit var coinTextView: TextView
    private lateinit var levelTextView: TextView
    private lateinit var toWordBtn: Button
    private lateinit var toQuizBtn: Button
    private lateinit var toAksaraBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Cek apakah user sudah mengisi identity
        val sharedPref = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val name = sharedPref.getString("user_name", null) // Jika null, berarti belum mengisi

        if (name == null) {
            val intent = Intent(this, IdentityActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            greetingTextView = findViewById(R.id.greeting)
            coinTextView = findViewById(R.id.coinTv)
            levelTextView = findViewById(R.id.levelTv)
            toWordBtn = findViewById(R.id.toWordBtn)
            toQuizBtn = findViewById(R.id.toQuizBtn)
            toAksaraBtn = findViewById(R.id.toAksaraBtn)

            // Set nama pengguna
            greetingTextView.text = "Sampurasun, $name!"

            // Set coin dan level
            updateCoinAndLevel()

            toWordBtn.setOnClickListener {
                val intent = Intent(this, WordActivity::class.java)
                startActivity(intent)
            }

            toQuizBtn.setOnClickListener {
                val intent = Intent(this, QuizActivity::class.java)
                startActivity(intent)
            }

            toAksaraBtn.setOnClickListener {
                val intent = Intent(this, AksaraActivity::class.java)
                startActivity(intent)
            }

            coinTextView.setOnClickListener {
                val intent = Intent(this, RewardActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        updateCoinAndLevel() // Refresh coin dan level setiap kembali ke MainActivity
    }

    private fun updateCoinAndLevel() {
        // Ambil data coin dan level dari shared preferences atau game data
        val gamePref = getSharedPreferences("game_data", Context.MODE_PRIVATE)
        val coin = gamePref.getInt("coin", 0)
        val level = gamePref.getInt("level", 1)

        // Update TextView
        coinTextView.text = "Coin: $coin"
        levelTextView.text = "Level: $level"
    }
}