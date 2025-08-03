package com.valokafor.prontologin.util

import android.util.Patterns

object Validator {
    
    fun isValidEmail(email: String): Boolean {
        return email.isNotBlank() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
    
    fun isValidPassword(password: String): Boolean {
        return password.isNotBlank() && password.length >= 6
    }
    
    fun doPasswordsMatch(password: String, confirmPassword: String): Boolean {
        return password == confirmPassword
    }
    
    data class ValidationResult(
        val isValid: Boolean,
        val errorMessage: String? = null
    )
    
    fun validateEmail(email: String): ValidationResult {
        return when {
            email.isBlank() -> ValidationResult(false, "Email cannot be empty")
            !isValidEmail(email) -> ValidationResult(false, "Please enter a valid email address")
            else -> ValidationResult(true)
        }
    }
    
    fun validatePassword(password: String): ValidationResult {
        return when {
            password.isBlank() -> ValidationResult(false, "Password cannot be empty")
            password.length < 6 -> ValidationResult(false, "Password must be at least 6 characters")
            else -> ValidationResult(true)
        }
    }
    
    fun validatePasswordMatch(password: String, confirmPassword: String): ValidationResult {
        return when {
            confirmPassword.isBlank() -> ValidationResult(false, "Please confirm your password")
            !doPasswordsMatch(password, confirmPassword) -> ValidationResult(false, "Passwords do not match")
            else -> ValidationResult(true)
        }
    }
}