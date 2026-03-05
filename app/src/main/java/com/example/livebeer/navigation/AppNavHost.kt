package com.example.livebeer.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.livebeer.feature.welcome.WelcomeScreen
import com.example.livebeer.feature.auth.login.LoginScreen
import com.example.livebeer.feature.auth.code.CodeScreen
import com.example.livebeer.feature.auth.register.RegisterScreen
import com.example.livebeer.feature.main.MainScreenUnauthorized

@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "welcome"
    ) {
        composable("welcome") {
            WelcomeScreen(
                onLogin    = { navController.navigate("login") },
                onRegister = { navController.navigate("register") },
                onGuest    = { navController.navigate("main_unauth") }
            )
        }

        composable("login") {
            LoginScreen(
                onNext     = { navController.navigate("code") },
                onBack     = { navController.popBackStack() },
                onRegister = { navController.navigate("register") }
            )
        }

        composable("register") {
            RegisterScreen(
                onBack    = { navController.popBackStack() },
                onSuccess = { navController.navigate("code") }
            )
        }

        composable("code") {
            CodeScreen(
                onSuccess = { navController.navigate("main_unauth") },
                onBack    = { navController.popBackStack() }
            )
        }

        composable("main_unauth") {
            MainScreenUnauthorized()
        }
    }
}