package ru.shvetsov.music_player_feature.presentation.screen.music_player

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.shvetsov.common.enums.TrackSource
import ru.shvetsov.common.ui.screens.ErrorScreen
import ru.shvetsov.common.ui.screens.LoadingScreen
import ru.shvetsov.common.utils.UIText
import ru.shvetsov.music_player_feature.presentation.R
import ru.shvetsov.music_player_feature.presentation.screen.music_player.viewmodel.MusicPlayer

@Composable
fun MusicPlayerScreen(
    uiState: State<MusicPlayer.UiState>,
    isPlaying: State<Boolean>,
    progress: State<Long>,
    onEvent: (MusicPlayer.Event) -> Unit,
    elapsedTime: State<String>,
    trackDuration: State<String>
) {

    LaunchedEffect(key1 = Unit) {
        onEvent(MusicPlayer.Event.LoadTrack)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when {
            uiState.value.isLoading -> {
                LoadingScreen()
            }

            uiState.value.error !is UIText.EmptyString -> {
                ErrorScreen(message = uiState.value.error.asString())
            }

            uiState.value.data != null -> {
                key(uiState.value.data) {
                    MusicTrackItem(track = uiState.value.data!!)
                }
            }
        }

        Slider(
            value = progress.value.toFloat(),
            onValueChange = { newValue ->
                onEvent(MusicPlayer.Event.SeekTo(newValue.toLong()))
            },
            valueRange = 0f..(uiState.value.data?.duration?.let {
                if (uiState.value.data!!.source == TrackSource.API) it * 1000f else it.toFloat()
            } ?: 0f),
            colors = SliderDefaults.colors(
                thumbColor = colorResource(id = R.color.light_green),
                inactiveTrackColor = Color.LightGray,
                activeTrackColor = colorResource(id = R.color.light_green)
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = elapsedTime.value, color = Color.Gray, fontSize = 12.sp)
            Text(text = trackDuration.value, color = Color.Gray, fontSize = 12.sp)
        }

        MusicControls(
            isPlaying = isPlaying.value,
            onPlayPauseClick = {
                if (isPlaying.value) {
                    onEvent(MusicPlayer.Event.PauseTrack)
                } else {
                    onEvent(MusicPlayer.Event.ResumeTrack)
                }
            },
            onNextClick = {
                onEvent(MusicPlayer.Event.NextTrack)
            },
            onPreviousClick = {
                onEvent(MusicPlayer.Event.PreviousTrack)
            }
        )
    }
}