package ru.shvetsov.music_player_feature.presentation.screen.music_player

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.shvetsov.music_player_feature.presentation.R

@Composable
fun MusicControls(
    isPlaying: Boolean,
    onPlayPauseClick: () -> Unit,
    onNextClick: () -> Unit,
    onPreviousClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        IconButton(
            onClick = onPreviousClick
        ) {
            Image(
                painter = painterResource(id = R.drawable.previous),
                contentDescription = stringResource(
                    id = R.string.previous
                ),
                modifier = Modifier.size(48.dp)
            )
        }
        IconButton(onClick = onPlayPauseClick) {
            Image(
                painter = if (isPlaying) {
                    painterResource(id = R.drawable.pause)
                } else {
                    painterResource(id = R.drawable.play_arrow)
                },
                contentDescription = stringResource(id = R.string.play_pause),
                modifier = Modifier.size(48.dp)
            )
        }
        IconButton(onClick = onNextClick) {
            Image(
                painter = painterResource(id = R.drawable.next),
                contentDescription = stringResource(R.string.next),
                modifier = Modifier.size(48.dp)
            )
        }
    }
}