package com.example.sundarua

import android.content.Context
import android.content.SharedPreferences
import com.example.sundarua.test.UserPrefManager
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

class UserPrefManagerUnitTest {

    private lateinit var context: Context
    private lateinit var sharedPref: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    @Before
    fun setUp() {
        context = mock(Context::class.java)
        sharedPref = mock(SharedPreferences::class.java)
        editor = mock(SharedPreferences.Editor::class.java)

        `when`(context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)).thenReturn(sharedPref)
        `when`(sharedPref.edit()).thenReturn(editor)
        `when`(editor.putString(anyString(), anyString())).thenReturn(editor)
    }

    @Test
    fun saveUserName_callsPutStringAndApply() {
        UserPrefManager.saveUserName(context, "Ujang")
        verify(editor).putString("user_name", "Ujang")
        verify(editor).apply()
    }

    @Test
    fun isValid_withValidName() {
        assert(UserPrefManager.isValid("Ujang"))
    }

    @Test
    fun isValid_withEmptyName() {
        assert(!UserPrefManager.isValid("   "))
    }

    @Test
    fun isValid_withSymbolName() {
        assert(UserPrefManager.isValid("Wahyu!23"))
    }
}