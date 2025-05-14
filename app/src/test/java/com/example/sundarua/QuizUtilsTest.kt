package com.example.sundarua

import com.example.sundarua.test.QuizUtils
import org.junit.Assert.assertEquals
import org.junit.Test

class QuizUtilsTest {

    // Fungsi untuk menguji nilai berdasarkan jumlah jawaban benar (correct) dari total soal (total).
    @Test
    fun testCalculateScorePercentage() {
        assertEquals(0, QuizUtils.calculateScorePercentage(0, 5))
        assertEquals(20, QuizUtils.calculateScorePercentage(1, 5))
        assertEquals(40, QuizUtils.calculateScorePercentage(2, 5))
        assertEquals(60, QuizUtils.calculateScorePercentage(3, 5))
        assertEquals(80, QuizUtils.calculateScorePercentage(4, 5))
        assertEquals(100, QuizUtils.calculateScorePercentage(5, 5))
    }

    // Menguji jika pengguna lulus kuis (passed = true), maka:
    //	•	Koin bertambah (misal +100)
    //	•	Level naik (misal dari 3 jadi 4)
    @Test
    fun testUpdateCoinAndLevel_passed() {
        val result = QuizUtils.updateCoinAndLevel(200, 3, true)
        assertEquals(300, result.first)
        assertEquals(4, result.second)
    }

    // Menguji jika pengguna gagal kuis (passed = false), maka:
    //	•	Koin tetap
    //	•	Level tidak naik
    @Test
    fun testUpdateCoinAndLevel_failed() {
        val result = QuizUtils.updateCoinAndLevel(200, 3, false)
        assertEquals(200, result.first)
        assertEquals(3, result.second)
    }
}