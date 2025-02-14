package ru.shvetsov.musicplayerapplication.ui.screens

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController

@Composable
fun MusicApp(hasPermission: Boolean, onRequestPermission: () -> Unit) {
    val navController = rememberNavController()
    MainScreen(navController, hasPermission, onRequestPermission)
}