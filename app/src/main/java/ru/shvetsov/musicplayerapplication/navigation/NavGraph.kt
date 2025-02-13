package ru.shvetsov.musicplayerapplication.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun NavGraph(
    navHostController: NavHostController,
    modifier: Modifier = Modifier
) {

    NavHost(
        navController = navHostController,
        startDestination = Screen.RemoteMusic.route,
        modifier = modifier
    ) {
        composable(Screen.RemoteMusic.route) { }
        composable(Screen.LocalMusic.route) { }
    }
}