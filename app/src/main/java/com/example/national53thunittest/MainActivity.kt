package com.example.national53thunittest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.national53thunittest.ui.theme.National53thUnitTestTheme

val LocalAuthNavController = compositionLocalOf<NavHostController> { error("") }
val LocalRoomDataBase = compositionLocalOf<RoomDataBase> { error("") }

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            National53thUnitTestTheme {
                val db = getRoomDataBase(this)
                val navController = rememberNavController()

                LaunchedEffect(Unit) {
                    val users = UsersModel(db)
                    users.db.alive()
                }

                CompositionLocalProvider(
                    LocalAuthNavController provides navController,
                    LocalRoomDataBase provides db
                ) {
                    AuthRouter()
                }
            }
        }
    }
}