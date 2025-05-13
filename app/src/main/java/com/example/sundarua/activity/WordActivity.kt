package com.example.sundarua.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sundarua.adapter.WordAdapter
import com.example.sundarua.data.Word
import com.example.sundarua.databinding.ActivityWordBinding
import com.example.sundarua.service.ApiClient
import kotlinx.coroutines.launch

class WordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWordBinding
    private lateinit var adapter: WordAdapter
    private var listWord: List<Word> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityWordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Tombol kembali ke MainActivity
        binding.backMainBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            startActivity(intent)
            finish()
        }

        // Setup RecyclerView
        binding.rvWords.layoutManager = LinearLayoutManager(this)
        getData()

        // Setup SearchView
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false

            override fun onQueryTextChange(newText: String?): Boolean {
                lifecycleScope.launch {
                    newText?.let {
                        if (::adapter.isInitialized && listWord.isNotEmpty()) {
                            adapter.filterList(it)
                        }
                    }
                }
                return true
            }
        })
    }

    private fun getData() {
        lifecycleScope.launch {
            binding.progressBar.visibility = android.view.View.VISIBLE
            try {
                val response = ApiClient.apiService.getWords()
                listWord = response.words
                adapter = WordAdapter(listWord)
                binding.rvWords.adapter = adapter
            } catch (e: Exception) {
                Toast.makeText(this@WordActivity, "Antosan sakedap nuju nampilkeun data", Toast.LENGTH_SHORT).show()
            } finally {
                binding.progressBar.visibility = android.view.View.GONE
            }
        }
    }
}