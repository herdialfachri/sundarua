package com.example.sundarua

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sundarua.adapter.WordAdapter
import com.example.sundarua.data.Word
import com.example.sundarua.service.ApiClient
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: WordAdapter
    private lateinit var searchView: SearchView
    private var listWord: List<Word> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.rvWords)
        searchView = findViewById(R.id.searchView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        getData()

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                lifecycleScope.launch {
                    newText?.let {
                        adapter.filterList(it)
                    }
                }

                return true
            }
        })
    }

    private fun getData(){
        lifecycleScope.launch {
            try {
                val response = ApiClient.apiService.getWords()
                listWord = response.words
                adapter = WordAdapter(listWord)
                recyclerView.adapter = adapter
            } catch (e: Exception) {
                Toast.makeText(this@MainActivity, "Gagal ambil data", Toast.LENGTH_SHORT).show()
            }
        }
    }
}