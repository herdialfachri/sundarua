package com.example.sundarua.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.sundarua.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Cek apakah user sudah mengisi identity
        val sharedPref = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val name = sharedPref.getString("user_name", null) // Jika null, berarti belum mengisi

        if (name == null) {
            // Jika nama belum diisi, arahkan ke IdentityActivity
            val intent = Intent(this, IdentityActivity::class.java)
            startActivity(intent)
            finish() // Pastikan MainActivity tidak dapat diakses setelah IdentityActivity
        } else {
            // Jika nama sudah diisi, tampilkan nama pada greeting
            val greetingTextView = findViewById<TextView>(R.id.greeting)
            greetingTextView.text = "Sampurasun, $name!"
        }

        // Temukan tombol berdasarkan ID-nya
        val toWordBtn = findViewById<Button>(R.id.toWordBtn)
        val toQuizBtn = findViewById<Button>(R.id.toQuizBtn)

        // Set OnClickListener untuk toWordBtn
        toWordBtn.setOnClickListener {
            // Membuat Intent untuk berpindah ke WordActivity
            val intent = Intent(this, WordActivity::class.java)
            startActivity(intent)
        }

        // Set OnClickListener untuk toQuizBtn
        toQuizBtn.setOnClickListener {
            // Membuat Intent untuk berpindah ke QuizActivity
            val intent = Intent(this, QuizActivity::class.java)
            startActivity(intent)
        }
    }
}