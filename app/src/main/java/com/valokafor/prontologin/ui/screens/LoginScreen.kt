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
import com.valokafor.prontologin.ui.state.LoginUiState
import com.valokafor.prontologin.ui.utils.FormValidation
import com.valokafor.prontologin.ui.viewmodel.LoginViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val uiState by viewModel.uiState.collectAsState()
    val isFormValid = FormValidation.isLoginFormValid(email, password)

    // Navigate to home on successful login
    LaunchedEffect(uiState) {
        if (uiState is LoginUiState.Success) {
            navController.navigate("home") {
                popUpTo("login") { inclusive = true }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Login") }
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
                    text = "Welcome Back",
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

                when (val state = uiState) {
                    is LoginUiState.Error -> {
                        Text(
                            text = state.message,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    is LoginUiState.Success -> {
                        Text(
                            text = "Login successful!",
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    else -> {}
                }

                AuthButton(
                    onClick = { viewModel.login(email, password) },
                    text = "Sign In",
                    enabled = isFormValid,
                    isLoading = uiState is LoginUiState.Loading
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TextButton(
                        onClick = { navController.navigate("signup") }
                    ) {
                        Text("Sign Up")
                    }

                    TextButton(
                        onClick = { navController.navigate("forgot_password") }
                    ) {
                        Text("Forgot Password?")
                    }
                }
            }
        }
    }
}
