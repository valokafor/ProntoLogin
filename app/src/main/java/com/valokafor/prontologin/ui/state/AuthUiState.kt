package com.valokafor.prontologin.ui.state

sealed class AuthUiState {
    object Idle : AuthUiState()
    object Loading : AuthUiState()
    data class Success(val message: String) : AuthUiState()
    data class Error(val message: String) : AuthUiState()
}

sealed class LoginUiState {
    object Idle : LoginUiState()
    object Loading : LoginUiState()
    object Success : LoginUiState()
    data class Error(val message: String) : LoginUiState()
}

sealed class SignUpUiState {
    object Idle : SignUpUiState()
    object Loading : SignUpUiState()
    data class Success(val message: String) : SignUpUiState()
    data class Error(val message: String) : SignUpUiState()
}

sealed class ForgotPasswordUiState {
    object Idle : ForgotPasswordUiState()
    object Loading : ForgotPasswordUiState()
    data class Success(val message: String) : ForgotPasswordUiState()
    data class Error(val message: String) : ForgotPasswordUiState()
}
