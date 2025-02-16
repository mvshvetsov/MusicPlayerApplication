package ru.shvetsov.music_player_feature.presentation.screen.music_player

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import ru.shvetsov.music_player_feature.domain.model.MusicTrackModel
import ru.shvetsov.music_player_feature.presentation.R

@Composable
fun MusicTrackItem(track: MusicTrackModel) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(track.cover)
                .crossfade(true)
                .build(),
            contentDescription = stringResource(R.string.album_cover),
            contentScale = ContentScale.Crop,
            placeholder = painterResource(id = R.drawable.music_placeholder),
            error = painterResource(id = R.drawable.music_placeholder),
            modifier = Modifier.size(250.dp)
        )
        Text(
            text = if (track.title == null) stringResource(R.string.unknown_track_title) else track.title.toString(),
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = if (track.albumTitle == null) stringResource(R.string.unknown_album_title) else track.albumTitle.toString(),
            color = Color.Gray,
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = if (track.artist == null) stringResource(R.string.unknown_artist_name) else track.artist.toString(),
            color = Color.Gray,
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.height(10.dp))
    }
}