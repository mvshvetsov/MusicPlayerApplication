package ru.shvetsov.musicplayerapplication.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.google.gson.Gson
import ru.shvetsov.common.model.MusicTrack
import ru.shvetsov.common.ui.screens.PermissionRequestScreen
import ru.shvetsov.local_music_feature.presentation.LocalMusicTracksViewModel
import ru.shvetsov.music_player_feature.presentation.screen.music_player.MusicPlayerScreen
import ru.shvetsov.music_player_feature.presentation.screen.music_player.viewmodel.MusicPlayerViewModel
import ru.shvetsov.remote_music_feature.presentation.viewmodel.RemoteMusicTracksViewModel
import ru.shvetsov.track_list_common.screens.music_track.MusicListScreen
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

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
                onTrackClick = { selectedTrackIndex, trackList ->
                    navHostController.navigate(
                        Screen.MusicPlayer.createRoute(
                            selectedTrackIndex,
                            trackList
                        )
                    )
                }
            )
        }
        composable(Screen.LocalMusic.route) {
            if (hasPermission) {
                val localViewModel = hiltViewModel<LocalMusicTracksViewModel>()
                val localUiState = localViewModel.uiState.collectAsStateWithLifecycle()
                MusicListScreen(
                    uiState = localUiState,
                    onEvent = localViewModel::onEvent,
                    onTrackClick = { selectedTrackIndex, trackList ->
                        navHostController.navigate(
                            Screen.MusicPlayer.createRoute(
                                selectedTrackIndex,
                                trackList
                            )
                        )
                    }
                )
            } else {
                PermissionRequestScreen(onRequestPermission)
            }
        }
        composable(
            Screen.MusicPlayer.route,
            arguments = listOf(
                navArgument("selectedTrackIndex") { type = NavType.IntType },
                navArgument("trackList") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val musicPlayerViewModel = hiltViewModel<MusicPlayerViewModel>()

            val musicPlayerUiState = musicPlayerViewModel.uiState.collectAsStateWithLifecycle()
            val isPlaying = musicPlayerViewModel.isPlaying.collectAsStateWithLifecycle()
            val selectedTrackIndex = backStackEntry.arguments?.getInt("selectedTrackIndex") ?: 0
            val progress = musicPlayerViewModel.progress.collectAsStateWithLifecycle()

            val trackListJson = backStackEntry.arguments?.getString("trackList") ?: ""
            val decodedTrackListJson = URLDecoder.decode(trackListJson, StandardCharsets.UTF_8.toString())
            val trackList = Gson().fromJson(decodedTrackListJson, Array<MusicTrack>::class.java).toList()

            val elapsedTime = musicPlayerViewModel.elapsedTime.collectAsStateWithLifecycle()
            val trackDuration = musicPlayerViewModel.trackDuration.collectAsStateWithLifecycle()

            musicPlayerViewModel.savedStateHandle["selectedTrackIndex"] = selectedTrackIndex
            musicPlayerViewModel.savedStateHandle["trackList"] = trackList

            MusicPlayerScreen(
                uiState = musicPlayerUiState,
                onEvent = musicPlayerViewModel::onEvent,
                isPlaying = isPlaying,
                progress = progress,
                elapsedTime = elapsedTime,
                trackDuration = trackDuration
            )
        }
    }
}