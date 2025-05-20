package com.example.sundarua

import android.content.Context
import android.content.SharedPreferences
import com.example.sundarua.test.UserPrefManager
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.anyString
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

class UserPrefManagerUnitTest {

    private lateinit var context: Context
    private lateinit var sharedPref: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    @Before
    fun setUp() {
        context = mock(Context::class.java)
        sharedPref = mock(SharedPreferences::class.java)
        editor = mock(SharedPreferences.Editor::class.java)

        // Setup mock untuk SharedPreferences dan editor-nya
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
    fun getUserName_returnsCorrectValue() {
        `when`(sharedPref.getString("user_name", null)).thenReturn("Ujang")
        val result = UserPrefManager.getUserName(context)
        assertEquals("Ujang", result)
    }

    @Test
    fun isUserNameAvailable_whenUserExists_returnsTrue() {
        `when`(sharedPref.getString("user_name", null)).thenReturn("Wulan")
        val result = UserPrefManager.isUserNameAvailable(context)
        assertTrue(result)
    }

    @Test
    fun isUserNameAvailable_whenUserNotExists_returnsFalse() {
        `when`(sharedPref.getString("user_name", null)).thenReturn(null)
        val result = UserPrefManager.isUserNameAvailable(context)
        assertFalse(result)
    }

    @Test
    fun isValid_withValidName() {
        assertTrue(UserPrefManager.isValid("Ujang"))
    }

    @Test
    fun isValid_withEmptyName() {
        assertFalse(UserPrefManager.isValid("   "))
    }

    @Test
    fun isValid_withSymbolName() {
        assertTrue(UserPrefManager.isValid("Wahyu!23"))
    }
}