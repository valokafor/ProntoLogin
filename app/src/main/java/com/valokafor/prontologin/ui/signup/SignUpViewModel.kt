package com.valokafor.prontologin.ui.signup

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
class SignUpViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<SignUpUiState>(SignUpUiState.Idle)
    val uiState: StateFlow<SignUpUiState> = _uiState

    fun signUp(email: String, password: String, name: String) {
        viewModelScope.launch {
            _uiState.value = SignUpUiState.Loading
            when (val result = authRepository.signUp(email, password, name)) {
                is AuthResult.Success -> _uiState.value = SignUpUiState.Success
                is AuthResult.Error -> _uiState.value = SignUpUiState.Error(result.message)
            }
        }
    }
}
