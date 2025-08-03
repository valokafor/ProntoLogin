package com.valokafor.prontologin.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.valokafor.prontologin.presentation.auth.forgotpassword.ForgotPasswordScreen
import com.valokafor.prontologin.presentation.auth.login.LoginScreen
import com.valokafor.prontologin.presentation.auth.signup.SignUpScreen
import com.valokafor.prontologin.presentation.home.HomeScreen

sealed class AuthRoute(val route: String) {
    object Login : AuthRoute("login")
    object SignUp : AuthRoute("signup")
    object ForgotPassword : AuthRoute("forgot_password")
    object Home : AuthRoute("home")
}

@Composable
fun AuthNavHost(
    navController: NavHostController = rememberNavController(),
    authViewModel: AuthStateViewModel = hiltViewModel()
) {
    val authState by authViewModel.authState.collectAsState()
    
    LaunchedEffect(authState) {
        when (authState) {
            is AuthState.Authenticated -> {
                navController.navigate(AuthRoute.Home.route) {
                    popUpTo(0) { inclusive = true }
                }
            }
            is AuthState.Unauthenticated -> {
                navController.navigate(AuthRoute.Login.route) {
                    popUpTo(0) { inclusive = true }
                }
            }
            AuthState.Loading -> { /* Do nothing */ }
        }
    }
    
    val startDestination = when (authState) {
        is AuthState.Authenticated -> AuthRoute.Home.route
        else -> AuthRoute.Login.route
    }
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(AuthRoute.Login.route) {
            LoginScreen(
                onNavigateToSignUp = {
                    navController.navigate(AuthRoute.SignUp.route)
                },
                onNavigateToForgotPassword = {
                    navController.navigate(AuthRoute.ForgotPassword.route)
                },
                onLoginSuccess = {
                    // Auth state observer will handle navigation
                }
            )
        }
        
        composable(AuthRoute.SignUp.route) {
            SignUpScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onSignUpSuccess = {
                    // Auth state observer will handle navigation
                }
            )
        }
        
        composable(AuthRoute.ForgotPassword.route) {
            ForgotPasswordScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onPasswordResetSuccess = {
                    navController.navigate(AuthRoute.Login.route) {
                        popUpTo(AuthRoute.Login.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        
        composable(AuthRoute.Home.route) {
            HomeScreen(
                onNavigateToLogin = {
                    navController.navigate(AuthRoute.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
    }
}