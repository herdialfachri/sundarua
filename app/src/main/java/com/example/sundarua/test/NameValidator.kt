package com.example.sundarua.test

object NameValidator {
    fun isValid(name: String): Boolean {
        return name.trim().isNotEmpty()
    }
}