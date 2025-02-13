package ru.shvetsov.musicplayerapplication.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.shvetsov.track_list_common.screens.music_track.MusicListScreen
import ru.shvetsov.track_list_common.screens.music_track.viewmodel.MusicTracksViewModel

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
        composable(Screen.RemoteMusic.route) {
            val viewModel = hiltViewModel<MusicTracksViewModel>()
            val uiState = viewModel.uiState.collectAsStateWithLifecycle()
            MusicListScreen(uiState = uiState, onEvent = viewModel::onEvent, onTrackClick = {})
        }
        composable(Screen.LocalMusic.route) { }
    }
}