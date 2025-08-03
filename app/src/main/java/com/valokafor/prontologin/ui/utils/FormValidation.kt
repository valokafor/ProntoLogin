package com.valokafor.prontologin.ui.utils

import android.util.Patterns

object FormValidation {

    fun isValidEmail(email: String): Boolean {
        return email.isNotBlank() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isValidPassword(password: String): Boolean {
        return password.isNotBlank() && password.length >= 6
    }

    fun doPasswordsMatch(password: String, confirmPassword: String): Boolean {
        return password == confirmPassword && password.isNotBlank()
    }

    fun isLoginFormValid(email: String, password: String): Boolean {
        return isValidEmail(email) && isValidPassword(password)
    }

    fun isSignUpFormValid(email: String, password: String, confirmPassword: String): Boolean {
        return isValidEmail(email) && isValidPassword(password) && doPasswordsMatch(password, confirmPassword)
    }

    fun isForgotPasswordFormValid(email: String): Boolean {
        return isValidEmail(email)
    }
}
