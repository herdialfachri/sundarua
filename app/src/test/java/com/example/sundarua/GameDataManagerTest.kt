package com.example.sundarua

import com.example.sundarua.helper.GameDataManager
import org.junit.Assert.*
import org.junit.Test

class GameDataManagerTest {

    // Memastikan fungsi getGreeting(name) menghasilkan string "Sampurasun, $name!" dengan benar.
    @Test
    fun testGreeting() {
        val name = "Tester"
        val result = GameDataManager.getGreeting(name)
        assertEquals("Sampurasun, Tester!", result)
    }

    // Memastikan jika data coin dan level tidak ada di var pref, fungsi mengembalikan nilai default coin = 0 dan level = 0.
    @Test
    fun testGetCoinAndLevelDefault() {
        val pref = emptyMap<String, Int>()
        val (coin, level) = GameDataManager.getCoinAndLevel(pref)
        assertEquals(0, coin)
        assertEquals(0, level)
    }

    // Memastikan jika data coin dan level tersedia, fungsi mengembalikan nilai yang sesuai.
    @Test
    fun testGetCoinAndLevelFromData() {
        val pref = mapOf("coin" to 50, "level" to 3)
        val (coin, level) = GameDataManager.getCoinAndLevel(pref)
        assertEquals(50, coin)
        assertEquals(3, level)
    }
}