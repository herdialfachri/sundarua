package com.example.sundarua.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.sundarua.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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