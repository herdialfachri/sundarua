package com.example.sundarua.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
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
        binding = ActivityWordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        backToMain()
        setupRecyclerView()
        setupSearchListener()
        fetchWords()
    }

    private fun backToMain() {
        binding.backMainBtn.setOnClickListener {
            navigateToMainActivity()
        }
    }

    private fun setupRecyclerView() {
        binding.rvWords.layoutManager = LinearLayoutManager(this)
    }

    private fun setupSearchListener() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?) = false

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    if (::adapter.isInitialized && listWord.isNotEmpty()) {
                        adapter.filterList(it)
                    }
                }
                return true
            }
        })
    }

    private fun fetchWords() {
        showLoading(true)
        lifecycleScope.launch {
            try {
                val response = ApiClient.apiService.getWords()
                listWord = response.words
                adapter = WordAdapter(listWord)
                binding.rvWords.adapter = adapter
            } catch (e: Exception) {
                showToast("Antosan sakedap nuju nampilkeun data")
            } finally {
                showLoading(false)
            }
        }
    }

    private fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
        }
        startActivity(intent)
        finish()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}