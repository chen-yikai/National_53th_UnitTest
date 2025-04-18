package com.example.national53thunittest

import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun Router(nav: NavHostController) {
    val textFieldColors = TextFieldDefaults.colors(
        unfocusedContainerColor = Color.Transparent,
        focusedContainerColor = Color.Transparent,
        errorContainerColor = Color.Transparent
    )

    NavHost(navController = nav, startDestination = Screens.SignIn.name) {
        composable(Screens.SignIn.name) { SignIn(nav, textFieldColors) }
        composable(Screens.SignUp.name) { SignUp(nav) }
    }
}

enum class Screens {
    Home, SignIn, SignUp, AccountInfo, News
}