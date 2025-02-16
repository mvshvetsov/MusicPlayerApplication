package ru.shvetsov.track_list_common.screens.music_track

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.shvetsov.track_list_common.adapter.MusicTrackAdapter
import ru.shvetsov.common.model.MusicTrack

@Composable
fun MusicTrackList(
    tracks: List<MusicTrack>,
    onTrackClick: (Int, List<MusicTrack>) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val adapter = remember { MusicTrackAdapter(onTrackClick) }

    LaunchedEffect(tracks) {
        adapter.submitList(tracks)
    }

    AndroidView(
        factory = {
            RecyclerView(context).apply {
                layoutManager = LinearLayoutManager(context)
                this.adapter = adapter
            }
        },
        update = {
            it.adapter = adapter
        },
        modifier = modifier
    )
}