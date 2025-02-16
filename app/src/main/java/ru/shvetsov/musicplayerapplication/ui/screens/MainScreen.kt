package ru.shvetsov.musicplayerapplication.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ru.shvetsov.musicplayerapplication.navigation.BottomNavigationBar
import ru.shvetsov.musicplayerapplication.navigation.NavGraph
import ru.shvetsov.musicplayerapplication.navigation.Screen

@Composable
fun MainScreen(
    navController: NavHostController = rememberNavController(),
    hasPermission: Boolean,
    onRequestPermission: () -> Unit
) {

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    val screensWithBottomBar = listOf(
        Screen.RemoteMusic.route,
        Screen.LocalMusic.route
    )

    Scaffold(
        containerColor = Color.White,
        bottomBar = {
            if (currentRoute in screensWithBottomBar) {
                BottomNavigationBar(navController = navController)
            }
        }
    ) { paddingValues ->
        NavGraph(
            navHostController = navController,
            modifier = Modifier.padding(paddingValues),
            hasPermission = hasPermission,
            onRequestPermission = onRequestPermission
        )
    }
}