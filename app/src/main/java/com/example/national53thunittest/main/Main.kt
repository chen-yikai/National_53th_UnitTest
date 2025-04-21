package com.example.national53thunittest.main

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.util.TableInfo

val LocalMainNavController = compositionLocalOf<NavHostController> { error("") }
val LocalDrawerState = compositionLocalOf<DrawerState> { error("") }

@Composable
fun Main() {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    CompositionLocalProvider(
        LocalMainNavController provides navController,
        LocalDrawerState provides drawerState
    ) {
        ModalNavigationDrawer(drawerContent = {
            ModalDrawerSheet {
                NavigationDrawerItem(
                    label = { Text("最新消息") },
                    selected = (navController.currentDestination?.route
                        ?: "") == MainScreens.News.name,
                    onClick = {
                        navController.navigate(MainScreens.News.name)
                    }
                )
            }
        }) {
            NavHost(navController = navController, startDestination = MainScreens.Home.name) {
                composable(MainScreens.Home.name) { HomeScreen() }
                composable(MainScreens.Profile.name) { ProfileScreen() }
                composable(MainScreens.News.name) { NewsScreen() }
            }
        }
    }
}

enum class MainScreens {
    Home, Profile, News
}