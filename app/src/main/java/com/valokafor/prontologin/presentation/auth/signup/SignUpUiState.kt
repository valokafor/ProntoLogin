package com.valokafor.prontologin.presentation.auth.signup

import com.valokafor.prontologin.domain.model.User

data class SignUpUiState(
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isLoading: Boolean = false,
    val isPasswordVisible: Boolean = false,
    val isConfirmPasswordVisible: Boolean = false,
    val emailError: String? = null,
    val passwordError: String? = null,
    val confirmPasswordError: String? = null,
    val generalError: String? = null,
    val isSignUpSuccessful: Boolean = false,
    val signedUpUser: User? = null
) {
    val isFormValid: Boolean
        get() = email.isNotBlank() && 
                password.isNotBlank() && 
                confirmPassword.isNotBlank() &&
                emailError == null && 
                passwordError == null &&
                confirmPasswordError == null
}