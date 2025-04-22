package com.example.national53thunittest.main

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.room.util.TableInfo
import kotlinx.coroutines.launch

val LocalMainNavController = compositionLocalOf<NavHostController> { error("") }
val LocalDrawerState = compositionLocalOf<DrawerState> { error("") }

@Composable
fun Main() {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    CompositionLocalProvider(
        LocalMainNavController provides navController,
        LocalDrawerState provides drawerState
    ) {
        ModalNavigationDrawer(drawerState = drawerState, drawerContent = {
            ModalDrawerSheet() {
                Column(
                    modifier = Modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    NavigationDrawerItem(
                        label = { Text("最新消息") },
                        selected = (navController.currentDestination?.route
                            ?: "") == MainScreens.News.name,
                        onClick = {
                            navController.navigate(MainScreens.News.name)
                            scope.launch {
                                drawerState.close()
                            }
                        }
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 10.dp),
                        horizontalArrangement = Arrangement.End
                    ) {
                        IconButton(onClick = {
                            scope.launch {
                                drawerState.close()
                            }
                        }) {
                            Icon(
                                Icons.Default.ArrowBack,
                                contentDescription = ""
                            )
                        }
                    }
                }
            }
        }) {
            NavHost(navController = navController, startDestination = MainScreens.Home.name) {
                composable(MainScreens.Home.name) { HomeScreen() }
                composable(MainScreens.Profile.name) { ProfileScreen() }
                composable(MainScreens.News.name) { NewsScreen() }
                composable(
                    "${MainScreens.NewsDetail.name}/{id}",
                    arguments = listOf(navArgument("id") {
                        type =
                            NavType.IntType
                    }),
                    enterTransition = { slideInHorizontally { it } },
                    exitTransition = { slideOutHorizontally { -it } },
                    popEnterTransition = { slideInHorizontally { -it } },
                    popExitTransition = { slideOutHorizontally { it } }
                ) { backStackEntry ->
                    val id = backStackEntry.arguments?.getInt("id") ?: 0
                    NewsDetail(id)
                }
            }
        }
    }
}

enum class MainScreens {
    Home, Profile, News, NewsDetail
}