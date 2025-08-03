package com.valokafor.prontologin.ui.forgotpassword

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun ForgotPasswordScreen(
    viewModel: ForgotPasswordViewModel = hiltViewModel(),
    onNavigateToLogin: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()
    var email by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        if (uiState is ForgotPasswordUiState.Loading) {
            CircularProgressIndicator()
        } else {
            Button(
                onClick = { viewModel.sendPasswordResetEmail(email) },
                modifier = Modifier.fillMaxWidth(),
                enabled = email.isNotBlank()
            ) {
                Text("Send Reset Email")
            }
            TextButton(onClick = onNavigateToLogin) {
                Text("Back to Login")
            }
        }

        if (uiState is ForgotPasswordUiState.Success) {
            Text("Password reset email sent.", modifier = Modifier.padding(top = 8.dp))
        }

        if (uiState is ForgotPasswordUiState.Error) {
            Text(
                text = (uiState as ForgotPasswordUiState.Error).message,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}
