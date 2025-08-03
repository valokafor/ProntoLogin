package com.valokafor.prontologin.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.valokafor.prontologin.ui.components.AuthButton
import com.valokafor.prontologin.ui.components.AuthTextField
import com.valokafor.prontologin.ui.state.SignUpUiState
import com.valokafor.prontologin.ui.utils.FormValidation
import com.valokafor.prontologin.ui.viewmodel.SignUpViewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.getValue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    navController: NavController,
    viewModel: SignUpViewModel = hiltViewModel()
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    val uiState by viewModel.uiState.collectAsState()
    val isFormValid = FormValidation.isSignUpFormValid(email, password, confirmPassword)

    // Navigate back to login on successful signup
    LaunchedEffect(uiState) {
        if (uiState is SignUpUiState.Success) {
            navController.navigate("login") {
                popUpTo("login") { inclusive = true }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Sign Up") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Create Account",
                    style = MaterialTheme.typography.headlineMedium
                )

                AuthTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = "Email",
                    keyboardType = KeyboardType.Email,
                    isError = email.isNotBlank() && !FormValidation.isValidEmail(email),
                    supportingText = if (email.isNotBlank() && !FormValidation.isValidEmail(email)) {
                        "Please enter a valid email"
                    } else null
                )

                AuthTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = "Password",
                    isPassword = true,
                    isError = password.isNotBlank() && !FormValidation.isValidPassword(password),
                    supportingText = if (password.isNotBlank() && !FormValidation.isValidPassword(password)) {
                        "Password must be at least 6 characters"
                    } else null
                )

                AuthTextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    label = "Confirm Password",
                    isPassword = true,
                    isError = confirmPassword.isNotBlank() && !FormValidation.doPasswordsMatch(password, confirmPassword),
                    supportingText = if (confirmPassword.isNotBlank() && !FormValidation.doPasswordsMatch(password, confirmPassword)) {
                        "Passwords do not match"
                    } else null
                )

                when (val state = uiState) {
                    is SignUpUiState.Error -> {
                        Text(
                            text = state.message,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    is SignUpUiState.Success -> {
                        Text(
                            text = state.message,
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    else -> {}
                }

                AuthButton(
                    onClick = { viewModel.signUp(email, password) },
                    text = "Create Account",
                    enabled = isFormValid,
                    isLoading = uiState is SignUpUiState.Loading
                )

                TextButton(
                    onClick = { navController.navigateUp() }
                ) {
                    Text("Already have an account? Sign In")
                }
            }
        }
    }
}
