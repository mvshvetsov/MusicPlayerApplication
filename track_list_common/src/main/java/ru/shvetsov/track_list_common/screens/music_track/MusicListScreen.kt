package ru.shvetsov.track_list_common.screens.music_track

import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.shvetsov.common.ui.screens.ErrorScreen
import ru.shvetsov.common.ui.screens.LoadingScreen
import ru.shvetsov.common.utils.UIText
import ru.shvetsov.track_list_common.R
import ru.shvetsov.common.model.MusicTrack
import ru.shvetsov.track_list_common.screens.music_track.viewmodel.MusicTrackList

@Composable
fun MusicListScreen(
    uiState: State<MusicTrackList.UiState>,
    onEvent: (MusicTrackList.Event) -> Unit,
    onTrackClick: (MusicTrack) -> Unit
) {

    val stringQuery = remember { mutableStateOf("") }

    LaunchedEffect(key1 = Unit) {
        Log.d("State", "State: $uiState")
        if (uiState.value.data.isNullOrEmpty()) {
            onEvent(MusicTrackList.Event.FetchRemoteMusicTracks)
        }
    }

    Scaffold(topBar = {
        Surface(
            shadowElevation = 8.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            shape = RoundedCornerShape(20.dp)
        ) {
            TextField(
                value = stringQuery.value,
                onValueChange = {
                    stringQuery.value = it
                    onEvent(MusicTrackList.Event.SearchRemoteTracks(stringQuery.value))
                },
                placeholder = {
                    Text(
                        text = stringResource(R.string.search_for_a_songs_artists_albums),
                        fontSize = 14.sp,
                        color = Color.DarkGray
                    )
                },
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor = colorResource(id = R.color.light_green),
                    focusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent
                ),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = stringResource(R.string.search)
                    )
                },
                singleLine = true
            )
        }
    }
    ) { paddingValues ->

        when {
            uiState.value.isLoading -> {
                LoadingScreen()
            }

            uiState.value.error !is UIText.EmptyString -> {
                ErrorScreen(message = uiState.value.error.asString())
            }

            uiState.value.data != null -> {
                MusicTrackList(
                    tracks = uiState.value.data!!,
                    onTrackClick = onTrackClick,
                    modifier = Modifier.padding(paddingValues)
                )
            }
        }
    }
}