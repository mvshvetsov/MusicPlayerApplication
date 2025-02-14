package ru.shvetsov.musicplayerapplication.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.shvetsov.common.ui.screens.PermissionRequestScreen
import ru.shvetsov.local_music_feature.presentation.LocalMusicTracksViewModel
import ru.shvetsov.track_list_common.screens.music_track.MusicListScreen
import ru.shvetsov.track_list_common.screens.music_track.viewmodel.MusicTracksViewModel

@Composable
fun NavGraph(
    navHostController: NavHostController,
    modifier: Modifier = Modifier,
    hasPermission: Boolean,
    onRequestPermission: () -> Unit
) {

    NavHost(
        navController = navHostController,
        startDestination = Screen.RemoteMusic.route,
        modifier = modifier
    ) {
        composable(Screen.RemoteMusic.route) {
            MusicListScreen(uiState = uiState, onEvent = viewModel::onEvent, onTrackClick = {}, isLocal = false)
        }
        composable(Screen.LocalMusic.route) {
            if (hasPermission) {
                val localViewModel = hiltViewModel<LocalMusicTracksViewModel>()
                val localUiState = localViewModel.uiState.collectAsStateWithLifecycle()
                MusicListScreen(uiState = localUiState, onEvent = localViewModel::onEvent, onTrackClick = {}, isLocal = true)
            } else {
                PermissionRequestScreen(onRequestPermission)
            }
        }
    }
}