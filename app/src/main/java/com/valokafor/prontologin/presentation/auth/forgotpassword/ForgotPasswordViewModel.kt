package com.valokafor.prontologin.presentation.auth.forgotpassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.valokafor.prontologin.domain.usecase.ResetPasswordUseCase
import com.valokafor.prontologin.util.Validator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val resetPasswordUseCase: ResetPasswordUseCase
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(ForgotPasswordUiState())
    val uiState: StateFlow<ForgotPasswordUiState> = _uiState.asStateFlow()
    
    fun onEmailChange(email: String) {
        val emailValidation = Validator.validateEmail(email)
        _uiState.update { currentState ->
            currentState.copy(
                email = email,
                emailError = if (email.isNotEmpty()) emailValidation.errorMessage else null,
                generalError = null,
                successMessage = null
            )
        }
    }
    
    fun onSendResetEmail() {
        val currentState = _uiState.value
        
        val emailValidation = Validator.validateEmail(currentState.email)
        
        _uiState.update { state ->
            state.copy(
                emailError = emailValidation.errorMessage
            )
        }
        
        if (!emailValidation.isValid) {
            return
        }
        
        viewModelScope.launch {
            _uiState.update { 
                it.copy(
                    isLoading = true, 
                    generalError = null,
                    successMessage = null
                ) 
            }
            
            resetPasswordUseCase(
                email = currentState.email.trim()
            ).fold(
                onSuccess = {
                    _uiState.update { state ->
                        state.copy(
                            isLoading = false,
                            isResetEmailSent = true,
                            successMessage = "Password reset email sent to ${currentState.email}"
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
        _uiState.update { 
            it.copy(
                generalError = null,
                successMessage = null
            ) 
        }
    }
    
    fun resetState() {
        _uiState.value = ForgotPasswordUiState()
    }
}