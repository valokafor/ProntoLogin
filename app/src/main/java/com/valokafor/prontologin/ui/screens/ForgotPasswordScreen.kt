package com.valokafor.prontologin.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import com.valokafor.prontologin.ui.state.ForgotPasswordUiState
import com.valokafor.prontologin.ui.utils.FormValidation
import com.valokafor.prontologin.ui.viewmodel.ForgotPasswordViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForgotPasswordScreen(
    navController: NavController,
    viewModel: ForgotPasswordViewModel = hiltViewModel()
) {
    var email by remember { mutableStateOf("") }

    val uiState by viewModel.uiState.collectAsState()
    val isFormValid = FormValidation.isForgotPasswordFormValid(email)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Reset Password") },
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
                    text = "Forgot Password?",
                    style = MaterialTheme.typography.headlineMedium
                )

                Text(
                    text = "Enter your email address and we'll send you a link to reset your password.",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(bottom = 16.dp)
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

                when (val state = uiState) {
                    is ForgotPasswordUiState.Error -> {
                        Text(
                            text = state.message,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    is ForgotPasswordUiState.Success -> {
                        Text(
                            text = state.message,
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    else -> {}
                }

                AuthButton(
                    onClick = { viewModel.resetPassword(email) },
                    text = "Send Reset Link",
                    enabled = isFormValid,
                    isLoading = uiState is ForgotPasswordUiState.Loading
                )

                TextButton(
                    onClick = { navController.navigateUp() }
                ) {
                    Text("Back to Login")
                }
            }
        }
    }
}
