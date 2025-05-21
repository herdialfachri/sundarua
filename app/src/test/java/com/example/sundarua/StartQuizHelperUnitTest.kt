package com.example.sundarua

import android.content.Context
import android.content.SharedPreferences
import com.example.sundarua.test.StartQuizHelper
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

class StartQuizHelperUnitTest {

    private lateinit var context: Context
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    @Before
    fun setup() {
        context = mock(Context::class.java)
        sharedPreferences = mock(SharedPreferences::class.java)
        editor = mock(SharedPreferences.Editor::class.java)

        `when`(context.getSharedPreferences(anyString(), anyInt())).thenReturn(sharedPreferences)
        `when`(sharedPreferences.edit()).thenReturn(editor)
        `when`(editor.putInt(anyString(), anyInt())).thenReturn(editor)
    }

    @Test
    fun testCalculateScorePercentage() {
        assertEquals(100, StartQuizHelper.calculateScorePercentage(10, 10))
        assertEquals(50, StartQuizHelper.calculateScorePercentage(5, 10))
        assertEquals(0, StartQuizHelper.calculateScorePercentage(0, 10))
    }

    @Test
    fun testUpdateCoinAndLevel_passed() {
        val (coin, level) = StartQuizHelper.updateCoinAndLevel(100, 2, true)
        assertEquals(200, coin)
        assertEquals(3, level)
    }

    @Test
    fun testUpdateCoinAndLevel_failed() {
        val (coin, level) = StartQuizHelper.updateCoinAndLevel(100, 2, false)
        assertEquals(100, coin)
        assertEquals(2, level)
    }

    @Test
    fun testGetCoin() {
        `when`(sharedPreferences.getInt("coin", 0)).thenReturn(123)
        val coin = StartQuizHelper.getCoin(context)
        assertEquals(123, coin)
    }

    @Test
    fun testGetLevel() {
        `when`(sharedPreferences.getInt("level", 0)).thenReturn(5)
        val level = StartQuizHelper.getLevel(context)
        assertEquals(5, level)
    }

    @Test
    fun testSaveCoinAndLevel() {
        StartQuizHelper.saveCoinAndLevel(context, 150, 4)
        verify(editor).putInt("coin", 150)
        verify(editor).putInt("level", 4)
        verify(editor).apply()
    }
}