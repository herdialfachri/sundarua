package com.example.sundarua.helper

object NameValidator {
    fun isValid(name: String): Boolean {
        return name.trim().isNotEmpty()
    }
}