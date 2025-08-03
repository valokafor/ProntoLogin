package com.valokafor.prontologin.ui.forgotpassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.valokafor.prontologin.data.repository.AuthRepository
import com.valokafor.prontologin.domain.AuthResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<ForgotPasswordUiState>(ForgotPasswordUiState.Idle)
    val uiState: StateFlow<ForgotPasswordUiState> = _uiState

    fun sendPasswordResetEmail(email: String) {
        viewModelScope.launch {
            _uiState.value = ForgotPasswordUiState.Loading
            when (val result = authRepository.sendPasswordReset(email)) {
                is AuthResult.Success -> _uiState.value = ForgotPasswordUiState.Success
                is AuthResult.Error -> _uiState.value = ForgotPasswordUiState.Error(result.message)
            }
        }
    }
}
