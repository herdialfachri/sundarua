package com.example.sundarua.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.sundarua.databinding.ActivityIdentityBinding
import com.example.sundarua.helper.NameValidator

class IdentityActivity : AppCompatActivity() {

    private lateinit var binding: ActivityIdentityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIdentityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set OnClickListener untuk tombol saveNameButton
        binding.saveNameButton.setOnClickListener {
            val name = binding.nameEditText.text.toString().trim()

            if (NameValidator.isValid(name)) {
                // Tampilkan Toast
                Toast.makeText(this, "Wilujeung sumping $name", Toast.LENGTH_LONG).show()

                // Simpan nama ke SharedPreferences
                val sharedPref = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
                with(sharedPref.edit()) {
                    putString("user_name", name)
                    apply()
                }

                // Intent ke MainActivity
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                binding.nameEditText.error = "Nami henteu kénging kosong"
            }
        }
    }
}