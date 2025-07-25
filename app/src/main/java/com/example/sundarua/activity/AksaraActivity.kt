package com.example.sundarua.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sundarua.adapter.AksaraAdapter
import com.example.sundarua.databinding.ActivityAksaraBinding
import com.example.sundarua.model.AksaraModel
import com.example.sundarua.test.FirebaseHelper

class AksaraActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAksaraBinding

    private val ngalagenaList = ArrayList<AksaraModel>()
    private val swaraList = ArrayList<AksaraModel>()
    private val rarangkenList = ArrayList<AksaraModel>()
    private val angkaList = ArrayList<AksaraModel>()
    private val sasatoanList = ArrayList<AksaraModel>()

    private lateinit var ngalagenaAdapter: AksaraAdapter
    private lateinit var swaraAdapter: AksaraAdapter
    private lateinit var rarangkenAdapter: AksaraAdapter
    private lateinit var angkaAdapter: AksaraAdapter
    private lateinit var sasatoanAdapter: AksaraAdapter

    private var loadedDataCount = 0
    private val totalDataNodes = 5

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAksaraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerViews()
        loadAllData()
        setupBackButton()
    }

    private fun setupRecyclerViews() {
        ngalagenaAdapter = AksaraAdapter(ngalagenaList)
        swaraAdapter = AksaraAdapter(swaraList)
        rarangkenAdapter = AksaraAdapter(rarangkenList)
        angkaAdapter = AksaraAdapter(angkaList)
        sasatoanAdapter = AksaraAdapter(sasatoanList)

        binding.recyclerViewNgalagena.setupHorizontal(ngalagenaAdapter)
        binding.recyclerViewSwara.setupHorizontal(swaraAdapter)
        binding.recyclerViewRarangken.setupHorizontal(rarangkenAdapter)
        binding.recyclerViewAngka.setupHorizontal(angkaAdapter)
        binding.recyclerViewSasatoan.setupHorizontal(sasatoanAdapter)
    }

    private fun loadAllData() {
        FirebaseHelper.getAksaraData(
            "aksara_ngalagena", ngalagenaList, ngalagenaAdapter, ::onDataLoaded
        )
        FirebaseHelper.getAksaraData(
            "aksara_swara", swaraList, swaraAdapter, ::onDataLoaded
        )
        FirebaseHelper.getAksaraData(
            "rarangken", rarangkenList, rarangkenAdapter, ::onDataLoaded
        )
        FirebaseHelper.getAksaraData(
            "angka", angkaList, angkaAdapter, ::onDataLoaded
        )
        FirebaseHelper.getAksaraData(
            "sasatoan", sasatoanList, sasatoanAdapter, ::onDataLoaded
        )
    }

    private fun onDataLoaded(success: Boolean) {
        loadedDataCount++
        if (loadedDataCount == totalDataNodes) {
            binding.progressBar.visibility = View.GONE
            binding.nestedScrollView.visibility = View.VISIBLE

            if (!success) {
                Toast.makeText(this, "Sebagian data gagal dimuat.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupBackButton() {
        binding.backMainBtn.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun androidx.recyclerview.widget.RecyclerView.setupHorizontal(adapter: AksaraAdapter) {
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        this.adapter = adapter
    }
}