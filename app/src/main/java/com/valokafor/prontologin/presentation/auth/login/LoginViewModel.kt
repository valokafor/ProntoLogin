package com.valokafor.prontologin.presentation.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.valokafor.prontologin.domain.usecase.SignInUseCase
import com.valokafor.prontologin.util.Validator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()
    
    fun onEmailChange(email: String) {
        val emailValidation = Validator.validateEmail(email)
        _uiState.update { currentState ->
            currentState.copy(
                email = email,
                emailError = if (email.isNotEmpty()) emailValidation.errorMessage else null,
                generalError = null
            )
        }
    }
    
    fun onPasswordChange(password: String) {
        val passwordValidation = Validator.validatePassword(password)
        _uiState.update { currentState ->
            currentState.copy(
                password = password,
                passwordError = if (password.isNotEmpty()) passwordValidation.errorMessage else null,
                generalError = null
            )
        }
    }
    
    fun onTogglePasswordVisibility() {
        _uiState.update { currentState ->
            currentState.copy(isPasswordVisible = !currentState.isPasswordVisible)
        }
    }
    
    fun onSignIn() {
        val currentState = _uiState.value
        
        val emailValidation = Validator.validateEmail(currentState.email)
        val passwordValidation = Validator.validatePassword(currentState.password)
        
        _uiState.update { state ->
            state.copy(
                emailError = emailValidation.errorMessage,
                passwordError = passwordValidation.errorMessage
            )
        }
        
        if (!emailValidation.isValid || !passwordValidation.isValid) {
            return
        }
        
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, generalError = null) }
            
            signInUseCase(
                email = currentState.email.trim(),
                password = currentState.password
            ).fold(
                onSuccess = { user ->
                    _uiState.update { state ->
                        state.copy(
                            isLoading = false,
                            isLoginSuccessful = true,
                            loggedInUser = user
                        )
                    }
                },
                onFailure = { exception ->
                    _uiState.update { state ->
                        state.copy(
                            isLoading = false,
                            generalError = exception.message
                        )
                    }
                }
            )
        }
    }
    
    fun clearError() {
        _uiState.update { it.copy(generalError = null) }
    }
}