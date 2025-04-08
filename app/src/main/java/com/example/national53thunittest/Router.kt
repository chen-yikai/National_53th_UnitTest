package com.example.national53thunittest

import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun Router() {
    val navController = rememberNavController()

    val textFieldColors = TextFieldDefaults.colors(
        unfocusedContainerColor = Color.Transparent,
        focusedContainerColor = Color.Transparent,
        errorContainerColor = Color.Transparent
    )

    NavHost(navController = navController, startDestination = Screens.SignIn.name) {
        composable(Screens.SignIn.name) { SignIn(navController, textFieldColors) }
        composable(Screens.SignUp.name) { SignUp(navController) }
    }
}

enum class Screens {
    Home, SignIn, SignUp, AccountInfo, News
}