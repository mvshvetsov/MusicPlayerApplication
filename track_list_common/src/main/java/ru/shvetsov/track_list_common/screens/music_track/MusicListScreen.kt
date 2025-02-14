package ru.shvetsov.track_list_common.screens.music_track

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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import ru.shvetsov.common.model.MusicTrack
import ru.shvetsov.common.ui.screens.ErrorScreen
import ru.shvetsov.common.ui.screens.LoadingScreen
import ru.shvetsov.common.ui.screens.NothingToShowScreen
import ru.shvetsov.common.utils.UIText
import ru.shvetsov.track_list_common.R
import ru.shvetsov.track_list_common.event.MusicTrackEvent
import ru.shvetsov.track_list_common.state.MusicTrackUiState

@Composable
fun MusicListScreen(
    uiState: State<MusicTrackUiState>,
    onEvent: (MusicTrackEvent) -> Unit,
    onTrackClick: (MusicTrack) -> Unit
) {

    val stringQuery = rememberSaveable { mutableStateOf("") }

    LaunchedEffect(key1 = stringQuery.value) {
        delay(500)
        if (stringQuery.value.isEmpty()) {
            onEvent(MusicTrackEvent.FetchTracks)
        } else {
            onEvent(MusicTrackEvent.SearchTracks(stringQuery.value))
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
                onValueChange = { stringQuery.value = it },
                placeholder = {
                    Text(
                        text = stringResource(R.string.search_for_a_songs_artists),
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
                ErrorScreen(message = uiState.value.error!!.asString())
            }

            uiState.value.data != null -> {
                MusicTrackList(
                    tracks = uiState.value.data!!,
                    onTrackClick = onTrackClick,
                    modifier = Modifier.padding(paddingValues)
                )
            }

            else -> {
                NothingToShowScreen()
            }
        }
    }
}