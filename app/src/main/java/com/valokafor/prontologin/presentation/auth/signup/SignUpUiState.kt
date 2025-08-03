package com.valokafor.prontologin.presentation.auth.signup

import com.valokafor.prontologin.domain.model.User

data class SignUpUiState(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isLoading: Boolean = false,
    val isPasswordVisible: Boolean = false,
    val isConfirmPasswordVisible: Boolean = false,
    val nameError: String? = null,
    val emailError: String? = null,
    val passwordError: String? = null,
    val confirmPasswordError: String? = null,
    val generalError: String? = null,
    val isSignUpSuccessful: Boolean = false,
    val signedUpUser: User? = null
) {
    val isFormValid: Boolean
        get() = name.isNotBlank() &&
                email.isNotBlank() && 
                password.isNotBlank() && 
                confirmPassword.isNotBlank() &&
                nameError == null &&
                emailError == null && 
                passwordError == null &&
                confirmPasswordError == null
}