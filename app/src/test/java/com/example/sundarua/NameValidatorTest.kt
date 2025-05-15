package com.example.sundarua

import com.example.sundarua.test.NameValidator
import org.junit.Assert.*
import org.junit.Test

class NameValidatorTest {

    // Test jika memasukkan nama yang valid, fungsi mengembalikan true.
    @Test
    fun testNameValid() {
        val name = "Dede"
        assertTrue(NameValidator.isValid(name))
    }

    // Test jika memasukkan spasi 4x, fungsi mengembalikan false.
    @Test
    fun testNameWithSpacesOnly() {
        val name = "     "
        assertFalse(NameValidator.isValid(name))
    }

    // Test jika memasukkan nama kosong, fungsi mengembalikan false.
    @Test
    fun testEmptyName() {
        val name = ""
        assertFalse(NameValidator.isValid(name))
    }

    // Test jika memasukkan nama dengan karakter khusus, fungsi mengembalikan true.
    @Test
    fun testNameWithSpaceNumberSymbol() {
        val name = "   Agus123@#"
        assertTrue(NameValidator.isValid(name))
    }
}