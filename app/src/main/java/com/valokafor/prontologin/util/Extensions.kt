package com.valokafor.prontologin.util

import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException

fun Exception.toUserFriendlyMessage(): String {
    return when (this) {
        is FirebaseAuthWeakPasswordException -> "Password is too weak. Please use at least 6 characters."
        is FirebaseAuthInvalidCredentialsException -> "Invalid email or password. Please check and try again."
        is FirebaseAuthUserCollisionException -> "An account with this email already exists."
        is FirebaseAuthInvalidUserException -> "No account found with this email address."
        is FirebaseAuthException -> {
            when (errorCode) {
                "ERROR_EMAIL_ALREADY_IN_USE" -> "This email is already registered."
                "ERROR_INVALID_EMAIL" -> "Please enter a valid email address."
                "ERROR_WRONG_PASSWORD" -> "Incorrect password. Please try again."
                "ERROR_USER_NOT_FOUND" -> "No account found with this email."
                "ERROR_USER_DISABLED" -> "This account has been disabled."
                "ERROR_TOO_MANY_REQUESTS" -> "Too many failed attempts. Please try again later."
                "ERROR_NETWORK_REQUEST_FAILED" -> "Network error. Please check your connection."
                else -> "Authentication failed. Please try again."
            }
        }
        else -> message ?: "An unexpected error occurred. Please try again."
    }
}