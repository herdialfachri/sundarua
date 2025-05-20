package com.example.sundarua.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.sundarua.databinding.ActivityIdentityBinding
import com.example.sundarua.test.UserPrefManager

class IdentityActivity : AppCompatActivity() {

    private lateinit var binding: ActivityIdentityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIdentityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.saveNameButton.setOnClickListener {
            handleSaveName()
        }
    }

    private fun handleSaveName() {
        val name = binding.nameEditText.text.toString().trim()

        if (UserPrefManager.isValid(name)) {
            showWelcomeMessage(name)
            UserPrefManager.saveUserName(this, name)
            goToMainActivity()
        } else {
            binding.nameEditText.error = "Nami henteu kénging kosong"
        }
    }

    private fun showWelcomeMessage(name: String) {
        Toast.makeText(this, "Wilujeung sumping $name", Toast.LENGTH_LONG).show()
    }

    private fun goToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}