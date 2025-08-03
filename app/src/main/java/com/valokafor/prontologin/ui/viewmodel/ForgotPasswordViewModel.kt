package com.valokafor.prontologin.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.valokafor.prontologin.data.repository.AuthRepository
import com.valokafor.prontologin.ui.state.ForgotPasswordUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<ForgotPasswordUiState>(ForgotPasswordUiState.Idle)
    val uiState: StateFlow<ForgotPasswordUiState> = _uiState.asStateFlow()

    fun resetPassword(email: String) {
        viewModelScope.launch {
            _uiState.value = ForgotPasswordUiState.Loading

            val result = authRepository.resetPassword(email)

            _uiState.value = if (result.isSuccess) {
                ForgotPasswordUiState.Success(result.message ?: "Password reset email sent")
            } else {
                ForgotPasswordUiState.Error(result.message ?: "Failed to send reset email")
            }
        }
    }

    fun clearState() {
        _uiState.value = ForgotPasswordUiState.Idle
    }
}
