package com.valokafor.prontologin.presentation.auth.forgotpassword

data class ForgotPasswordUiState(
    val email: String = "",
    val isLoading: Boolean = false,
    val emailError: String? = null,
    val generalError: String? = null,
    val isResetEmailSent: Boolean = false,
    val successMessage: String? = null
) {
    val isFormValid: Boolean
        get() = email.isNotBlank() && emailError == null
}