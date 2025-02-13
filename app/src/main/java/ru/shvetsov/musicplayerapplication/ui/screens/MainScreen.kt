package ru.shvetsov.musicplayerapplication.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ru.shvetsov.musicplayerapplication.navigation.BottomNavigationBar
import ru.shvetsov.musicplayerapplication.navigation.NavGraph

@Composable
fun MainScreen(navController: NavHostController = rememberNavController()) {
    Scaffold(
        containerColor = Color.White,
        bottomBar = { BottomNavigationBar(navController = navController) }
    ) { paddingValues ->
        NavGraph(navHostController = navController, modifier = Modifier.padding(paddingValues))
    }
}