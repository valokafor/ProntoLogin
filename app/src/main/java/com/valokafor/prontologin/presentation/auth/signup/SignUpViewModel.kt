package com.valokafor.prontologin.presentation.auth.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.valokafor.prontologin.data.repository.AuthRepository
import com.valokafor.prontologin.domain.usecase.SignUpUseCase
import com.valokafor.prontologin.util.Validator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase,
    private val authRepository: AuthRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(SignUpUiState())
    val uiState: StateFlow<SignUpUiState> = _uiState.asStateFlow()
    
    fun onNameChange(name: String) {
        _uiState.update { currentState ->
            currentState.copy(
                name = name,
                nameError = if (name.isNotEmpty() && name.isBlank()) "Name cannot be empty" else null,
                generalError = null
            )
        }
    }
    
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
        
        // Re-validate confirm password if it's not empty
        if (_uiState.value.confirmPassword.isNotEmpty()) {
            validatePasswordMatch()
        }
    }
    
    fun onConfirmPasswordChange(confirmPassword: String) {
        _uiState.update { currentState ->
            currentState.copy(
                confirmPassword = confirmPassword,
                generalError = null
            )
        }
        
        if (confirmPassword.isNotEmpty()) {
            validatePasswordMatch()
        } else {
            _uiState.update { it.copy(confirmPasswordError = null) }
        }
    }
    
    private fun validatePasswordMatch() {
        val currentState = _uiState.value
        val passwordMatchValidation = Validator.validatePasswordMatch(
            currentState.password,
            currentState.confirmPassword
        )
        
        _uiState.update { state ->
            state.copy(
                confirmPasswordError = passwordMatchValidation.errorMessage
            )
        }
    }
    
    fun onTogglePasswordVisibility() {
        _uiState.update { currentState ->
            currentState.copy(isPasswordVisible = !currentState.isPasswordVisible)
        }
    }
    
    fun onToggleConfirmPasswordVisibility() {
        _uiState.update { currentState ->
            currentState.copy(isConfirmPasswordVisible = !currentState.isConfirmPasswordVisible)
        }
    }
    
    fun onSignUp() {
        val currentState = _uiState.value
        
        val nameValid = currentState.name.trim().isNotBlank()
        val emailValidation = Validator.validateEmail(currentState.email)
        val passwordValidation = Validator.validatePassword(currentState.password)
        val passwordMatchValidation = Validator.validatePasswordMatch(
            currentState.password,
            currentState.confirmPassword
        )
        
        _uiState.update { state ->
            state.copy(
                nameError = if (!nameValid) "Name is required" else null,
                emailError = emailValidation.errorMessage,
                passwordError = passwordValidation.errorMessage,
                confirmPasswordError = passwordMatchValidation.errorMessage
            )
        }
        
        if (!nameValid ||
            !emailValidation.isValid || 
            !passwordValidation.isValid || 
            !passwordMatchValidation.isValid) {
            return
        }
        
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, generalError = null) }
            
            signUpUseCase(
                email = currentState.email.trim(),
                password = currentState.password
            ).fold(
                onSuccess = { user ->
                    // Update display name after successful signup
                    authRepository.updateDisplayName(currentState.name.trim()).fold(
                        onSuccess = {
                            _uiState.update { state ->
                                state.copy(
                                    isLoading = false,
                                    isSignUpSuccessful = true,
                                    signedUpUser = user
                                )
                            }
                        },
                        onFailure = { exception ->
                            // Even if display name update fails, signup was successful
                            _uiState.update { state ->
                                state.copy(
                                    isLoading = false,
                                    isSignUpSuccessful = true,
                                    signedUpUser = user
                                )
                            }
                        }
                    )
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