package com.example.sundarua.test

import android.content.Context
import androidx.core.content.edit

object UserPrefManager {
    private const val PREF_NAME = "user_prefs"
    private const val KEY_USER_NAME = "user_name"

    //Di main activity
    fun getGreeting(name: String): String {
        return "Sampurasun, $name!"
    }

    fun getCoinAndLevel(pref: Map<String, Int>): Pair<Int, Int> {
        val coin = pref["coin"] ?: 0
        val level = pref["level"] ?: 0
        return Pair(coin, level)
    }

    fun getUserName(context: Context): String? {
        val sharedPref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sharedPref.getString(KEY_USER_NAME, null)
    }

    fun isUserNameAvailable(context: Context): Boolean {
        return !getUserName(context).isNullOrEmpty()
    }

    // Di identity activity
    fun isValid(name: String): Boolean {
        return name.trim().isNotEmpty()
    }

    fun saveUserName(context: Context, name: String) {
        val sharedPref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        sharedPref.edit() { putString(KEY_USER_NAME, name) }
    }
}