package com.example.sundarua

import com.example.sundarua.helper.NameValidator
import org.junit.Assert.*
import org.junit.Test

class NameValidatorTest {

    @Test
    fun testNameValid() {
        val name = "Dede"
        assertTrue(NameValidator.isValid(name))
    }

    @Test
    fun testNameWithSpacesOnly() {
        val name = "     "
        assertFalse(NameValidator.isValid(name))
    }

    @Test
    fun testEmptyName() {
        val name = ""
        assertFalse(NameValidator.isValid(name))
    }

    @Test
    fun testNameWithLeadingAndTrailingSpaces() {
        val name = "   Asep   "
        assertTrue(NameValidator.isValid(name))
    }
}