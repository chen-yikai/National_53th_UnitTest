package com.example.national53thunittest

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.national53thunittest.main.MainRouter

@Composable
fun AuthRouter() {
    val navController = LocalAuthNavController.current

    NavHost(
        navController = navController, startDestination = AuthScreens.SignIn.name
    ) {
        composable(AuthScreens.SignIn.name) { SignIn() }
        composable(AuthScreens.SignUp.name) { SignUp() }
        composable(AuthScreens.Main.name) { MainRouter() }
    }
}

enum class AuthScreens {
    Main, SignIn, SignUp
}