package com.example.sundarua.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sundarua.R
import com.example.sundarua.model.AksaraModel
import com.squareup.picasso.Picasso

class AksaraAdapter(private val aksaraList: List<AksaraModel>) : RecyclerView.Adapter<AksaraAdapter.AksaraViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AksaraViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_aksara, parent, false)
        return AksaraViewHolder(view)
    }

    override fun onBindViewHolder(holder: AksaraViewHolder, position: Int) {
        val aksara = aksaraList[position]
        Picasso.get().load(aksara.imageUrl).into(holder.imageView)
        holder.descriptionTextView.text = aksara.description
    }

    override fun getItemCount(): Int = aksaraList.size

    inner class AksaraViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
        val descriptionTextView: TextView = itemView.findViewById(R.id.descriptionTextView)
    }
}