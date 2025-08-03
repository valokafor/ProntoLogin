package com.valokafor.prontologin.data.model

data class LoginRequest(
    val email: String,
    val password: String
)

data class SignUpRequest(
    val email: String,
    val password: String
)

data class ForgotPasswordRequest(
    val email: String
)

data class AuthResult(
    val isSuccess: Boolean,
    val message: String? = null,
    val exception: Exception? = null
)
