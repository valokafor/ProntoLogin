package com.valokafor.prontologin.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.valokafor.prontologin.presentation.auth.forgotpassword.ForgotPasswordScreen
import com.valokafor.prontologin.presentation.auth.login.LoginScreen
import com.valokafor.prontologin.presentation.auth.signup.SignUpScreen

sealed class AuthRoute(val route: String) {
    object Login : AuthRoute("login")
    object SignUp : AuthRoute("signup")
    object ForgotPassword : AuthRoute("forgot_password")
}

@Composable
fun AuthNavHost(
    navController: NavHostController = rememberNavController(),
    startDestination: String = AuthRoute.Login.route
) {
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
                }
            )
        }
        
        composable(AuthRoute.SignUp.route) {
            SignUpScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onSignUpSuccess = {
                    navController.navigate(AuthRoute.Login.route) {
                        popUpTo(AuthRoute.Login.route) {
                            inclusive = true
                        }
                    }
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
    }
}