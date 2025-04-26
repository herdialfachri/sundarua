package com.example.sundarua.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.sundarua.R
import com.example.sundarua.databinding.ActivityIdentityBinding

class IdentityActivity : AppCompatActivity() {

    private lateinit var binding: ActivityIdentityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIdentityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set OnClickListener untuk tombol saveNameButton
        binding.saveNameButton.setOnClickListener {
            val name = binding.nameEditText.text.toString().trim()

            // Cek jika nama tidak kosong
            if (name.isNotEmpty()) {
                // Simpan nama ke SharedPreferences
                val sharedPref = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
                with(sharedPref.edit()) {
                    putString("user_name", name) // Menyimpan nama pengguna
                    apply() // Simpan perubahan
                }

                // Intent ke MainActivity setelah berhasil
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish() // Menutup IdentityActivity agar tidak bisa kembali lagi
            } else {
                // Menampilkan error jika nama kosong
                binding.nameEditText.error = "Nami henteu kénging kosong"
            }
        }
    }
}