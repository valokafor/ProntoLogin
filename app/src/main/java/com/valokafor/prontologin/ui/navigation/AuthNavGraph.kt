package com.valokafor.prontologin.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.valokafor.prontologin.ui.screens.ForgotPasswordScreen
import com.valokafor.prontologin.ui.screens.LoginScreen
import com.valokafor.prontologin.ui.screens.SignUpScreen

object AuthDestinations {
    const val LOGIN = "login"
    const val SIGNUP = "signup"
    const val FORGOT_PASSWORD = "forgot_password"
}

@Composable
fun AuthNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = AuthDestinations.LOGIN
    ) {
        composable(AuthDestinations.LOGIN) {
            LoginScreen(navController = navController)
        }

        composable(AuthDestinations.SIGNUP) {
            SignUpScreen(navController = navController)
        }

        composable(AuthDestinations.FORGOT_PASSWORD) {
            ForgotPasswordScreen(navController = navController)
        }
    }
}
