package com.example.sundarua.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sundarua.adapter.QuizListAdapter
import com.example.sundarua.databinding.ActivityQuizBinding
import com.example.sundarua.model.QuizModel
import com.example.sundarua.test.FirebaseHelper

class QuizActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuizBinding
    private lateinit var quizList: MutableList<QuizModel>
    private lateinit var adapter: QuizListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)

        quizList = mutableListOf()
        setupBackButton()
        loadQuizData()
        updateCoinView()
    }

    override fun onResume() {
        super.onResume()
        updateCoinView()
    }

    private fun setupBackButton() {
        binding.backMainBtn.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            })
            finish()
        }
    }

    private fun updateCoinView() {
        val gamePref = getSharedPreferences("game_data", MODE_PRIVATE)
        val coin = gamePref.getInt("coin", 0)
        binding.quizzCoin.text = "Koin: $coin"
    }

    private fun setupRecyclerView() {
        adapter = QuizListAdapter(quizList)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }

    private fun loadQuizData() {
        binding.progressBar.visibility = View.VISIBLE

        FirebaseHelper.getQuizList { result ->
            binding.progressBar.visibility = View.GONE

            if (result.success) {
                quizList.clear()
                quizList.addAll(result.data)
                setupRecyclerView()
            } else {
                Toast.makeText(this, "Gagal memuat data kuis", Toast.LENGTH_SHORT).show()
            }
        }
    }
}