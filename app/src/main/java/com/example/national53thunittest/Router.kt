package com.example.national53thunittest

import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.national53thunittest.main.HomeScreen

@Composable
fun Router() {
    val navController = LocalRootNavController.current
    val context = LocalContext.current

    NavHost(navController = navController, startDestination = Screens.SignIn.name) {
        composable(Screens.SignIn.name) { SignIn() }
        composable(Screens.SignUp.name) { SignUp() }
        composable(Screens.Home.name) { HomeScreen() }
    }
}

enum class Screens {
    Home, SignIn, SignUp, AccountInfo, News
}