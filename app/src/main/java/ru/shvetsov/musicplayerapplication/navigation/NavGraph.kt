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
import ru.shvetsov.remote_music_feature.presentation.viewmodel.RemoteMusicTracksViewModel
import ru.shvetsov.track_list_common.screens.music_track.MusicListScreen

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
            val remoteMusicTracksViewModel = hiltViewModel<RemoteMusicTracksViewModel>()
            val remoteUiState = remoteMusicTracksViewModel.uiState.collectAsStateWithLifecycle()
            MusicListScreen(
                uiState = remoteUiState,
                onEvent = remoteMusicTracksViewModel::onEvent,
                onTrackClick = {})
        }
        composable(Screen.LocalMusic.route) {
            if (hasPermission) {
                val localViewModel = hiltViewModel<LocalMusicTracksViewModel>()
                val localUiState = localViewModel.uiState.collectAsStateWithLifecycle()
                MusicListScreen(
                    uiState = localUiState,
                    onEvent = localViewModel::onEvent,
                    onTrackClick = {})
            } else {
                PermissionRequestScreen(onRequestPermission)
            }
        }
    }
}