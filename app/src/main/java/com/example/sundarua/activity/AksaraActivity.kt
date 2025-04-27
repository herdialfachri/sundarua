package com.example.sundarua.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sundarua.adapter.AksaraAdapter
import com.example.sundarua.databinding.ActivityAksaraBinding
import com.example.sundarua.model.AksaraModel
import com.google.firebase.database.*

class AksaraActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAksaraBinding
    private val aksaraList = mutableListOf<AksaraModel>()
    private lateinit var adapter: AksaraAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAksaraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up RecyclerView
        adapter = AksaraAdapter(aksaraList)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        // Ambil data dari Firebase
        getAksaraData()
    }

    private fun getAksaraData() {
        binding.progressBar.visibility = View.VISIBLE

        // Firebase reference untuk mengambil data aksara
        val database = FirebaseDatabase.getInstance("https://sundarua-id-default-rtdb.asia-southeast1.firebasedatabase.app/")
        val aksaraRef = database.reference.child("aksara_ngalagena")

        aksaraRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                aksaraList.clear()

                if (snapshot.exists()) {
                    for (aksaraSnapshot in snapshot.children) {
                        val aksara = aksaraSnapshot.getValue(AksaraModel::class.java)
                        if (aksara != null) {
                            aksaraList.add(aksara)
                        }
                    }
                    adapter.notifyDataSetChanged()
                }
                binding.progressBar.visibility = View.GONE
            }

            override fun onCancelled(error: DatabaseError) {
                binding.progressBar.visibility = View.GONE
                Toast.makeText(this@AksaraActivity, "Failed to load data.", Toast.LENGTH_SHORT).show()
            }
        })
    }
}