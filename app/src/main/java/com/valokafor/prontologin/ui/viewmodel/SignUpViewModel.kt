package com.valokafor.prontologin.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.valokafor.prontologin.data.repository.AuthRepository
import com.valokafor.prontologin.ui.state.SignUpUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<SignUpUiState>(SignUpUiState.Idle)
    val uiState: StateFlow<SignUpUiState> = _uiState.asStateFlow()

    fun signUp(email: String, password: String, fullName: String) {
        viewModelScope.launch {
            _uiState.value = SignUpUiState.Loading

            val result = authRepository.signUp(email, password, fullName)

            _uiState.value = if (result.isSuccess) {
                SignUpUiState.Success(result.message ?: "Account created successfully")
            } else {
                SignUpUiState.Error(result.message ?: "Sign up failed")
            }
        }
    }

    fun clearState() {
        _uiState.value = SignUpUiState.Idle
    }
}
