package com.example.sundarua.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sundarua.R
import com.example.sundarua.adapter.AksaraAdapter
import com.example.sundarua.databinding.ActivityAksaraBinding
import com.example.sundarua.model.AksaraModel
import com.google.firebase.database.*

class AksaraActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAksaraBinding

    private lateinit var ngalagenaAdapter: AksaraAdapter
    private lateinit var swaraAdapter: AksaraAdapter
    private lateinit var rarangkenAdapter: AksaraAdapter
    private lateinit var angkaAdapter: AksaraAdapter
    private lateinit var sasatoanAdapter: AksaraAdapter

    private val ngalagenaList = ArrayList<AksaraModel>()
    private val swaraList = ArrayList<AksaraModel>()
    private val rarangkenList = ArrayList<AksaraModel>()
    private val angkaList = ArrayList<AksaraModel>()
    private val sasatoanList = ArrayList<AksaraModel>()

    private var loadedDataCount = 0
    private val totalDataNodes = 5

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAksaraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerViews()

        // Load data dari semua node
        getAksaraData("aksara_ngalagena", ngalagenaList, ngalagenaAdapter)
        getAksaraData("aksara_swara", swaraList, swaraAdapter)
        getAksaraData("rarangken", rarangkenList, rarangkenAdapter)
        getAksaraData("angka", angkaList, angkaAdapter)
        getAksaraData("sasatoan", sasatoanList, sasatoanAdapter)

        val backToMainBtn = findViewById<ImageView>(R.id.back_main_btn)

        backToMainBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun setupRecyclerViews() {
        ngalagenaAdapter = AksaraAdapter(ngalagenaList)
        swaraAdapter = AksaraAdapter(swaraList)
        rarangkenAdapter = AksaraAdapter(rarangkenList)
        angkaAdapter = AksaraAdapter(angkaList)
        sasatoanAdapter = AksaraAdapter(sasatoanList)

        binding.recyclerViewNgalagena.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewNgalagena.adapter = ngalagenaAdapter

        binding.recyclerViewSwara.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewSwara.adapter = swaraAdapter

        binding.recyclerViewRarangken.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewRarangken.adapter = rarangkenAdapter

        binding.recyclerViewAngka.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewAngka.adapter = angkaAdapter

        binding.recyclerViewSasatoan.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewSasatoan.adapter = sasatoanAdapter
    }

    private fun getAksaraData(nodeName: String, list: ArrayList<AksaraModel>, adapter: AksaraAdapter) {
        val database = FirebaseDatabase.getInstance("https://sundarua-id-default-rtdb.asia-southeast1.firebasedatabase.app/")
        val aksaraRef = database.reference.child(nodeName)

        aksaraRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                list.clear()
                if (snapshot.exists()) {
                    for (aksaraSnapshot in snapshot.children) {
                        val aksara = aksaraSnapshot.getValue(AksaraModel::class.java)
                        if (aksara != null) {
                            list.add(aksara)
                        }
                    }
                    adapter.notifyDataSetChanged()
                }
                loadedDataCount++
                if (loadedDataCount == totalDataNodes) {
                    binding.progressBar.visibility = View.GONE
                    binding.nestedScrollView.visibility = View.VISIBLE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@AksaraActivity, "Failed to load data.", Toast.LENGTH_SHORT).show()
                loadedDataCount++
                if (loadedDataCount == totalDataNodes) {
                    binding.progressBar.visibility = View.GONE
                    binding.nestedScrollView.visibility = View.VISIBLE
                }
            }
        })
    }
}