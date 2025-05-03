package com.example.sundarua.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sundarua.adapter.QuizListAdapter
import com.example.sundarua.databinding.ActivityQuizBinding
import com.example.sundarua.model.QuizModel
import com.google.firebase.database.FirebaseDatabase

class QuizActivity : AppCompatActivity() {
    private lateinit var binding: ActivityQuizBinding
    private lateinit var quizModelList: MutableList<QuizModel>
    private lateinit var adapter: QuizListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Tampilkan coin dari SharedPreferences
        updateCoinView()

        // Tombol kembali ke MainActivity
        binding.backMainBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            startActivity(intent)
            finish()
        }

        quizModelList = mutableListOf()
        getDataFromFirebase()
    }

    override fun onResume() {
        super.onResume()
        updateCoinView() // Refresh coin saat kembali ke activity ini
    }

    private fun updateCoinView() {
        val gamePref = getSharedPreferences("game_data", MODE_PRIVATE)
        val coin = gamePref.getInt("coin", 0)
        binding.quizzCoin.text = "Koin: $coin"
    }

    private fun setupRecyclerView() {
        binding.progressBar.visibility = View.GONE
        adapter = QuizListAdapter(quizModelList)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }

    private fun getDataFromFirebase() {
        binding.progressBar.visibility = View.VISIBLE

        val database = FirebaseDatabase.getInstance("https://sundarua-id-default-rtdb.asia-southeast1.firebasedatabase.app/")
        val reference = database.reference.child("quizzes")

        reference.get()
            .addOnSuccessListener { dataSnapshot ->
                if (dataSnapshot.exists()) {
                    for (snapshot in dataSnapshot.children) {
                        val quizModel = snapshot.getValue(QuizModel::class.java)
                        if (quizModel != null) {
                            quizModelList.add(quizModel)
                        }
                    }
                }
                setupRecyclerView()
            }
    }
}