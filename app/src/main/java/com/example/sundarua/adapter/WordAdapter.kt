package com.example.sundarua.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sundarua.R
import com.example.sundarua.data.Word

class WordAdapter(private var list: List<Word>) : RecyclerView.Adapter<WordAdapter.ViewHolder>() {
    private var filteredList: List<Word> = list

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val soranganText: TextView = view.findViewById(R.id.tvSorangan)
        val baturText: TextView = view.findViewById(R.id.tvBatur)
        val lomaText: TextView = view.findViewById(R.id.tvLoma)
        val bindoText: TextView = view.findViewById(R.id.tvBindo)
//        val englishText: TextView = view.findViewById(R.id.tvEnglish)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_word, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = filteredList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val word = filteredList[position]
        holder.soranganText.text = word.sorangan
        holder.baturText.text = word.batur
        holder.lomaText.text = word.loma
        holder.bindoText.text = word.bindo
//        holder.englishText.text = word.english
    }

    fun filterList(query: String) {
        filteredList = if (query.isEmpty()) {
            list
        } else {
            list.filter {
                it.sorangan.contains(query, ignoreCase = true) ||
                        it.batur.contains(query, ignoreCase = true) ||
                        it.loma.contains(query, ignoreCase = true) ||
                        it.bindo.contains(query, ignoreCase = true)
//                        it.english.contains(query, ignoreCase = true)
            }
        }
        notifyDataSetChanged()
    }
}