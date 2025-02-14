package ru.shvetsov.remote_music_feature.data.mappers

import ru.shvetsov.common.model.MusicTrack
import ru.shvetsov.remote_music_feature.data.models.Data

fun Data.toMusicTrackModel(): MusicTrack? {
    val trackId = id ?: return null
    val trackTitle = title ?: return null
    val artistName = artist?.name ?: return null
    val coverUrl = album?.cover ?: ""
    val duration = duration ?: return null
    val albumTitle = album?.title ?: return null
    val trackUri = preview ?: return null

    return MusicTrack(
        id = trackId,
        title = trackTitle,
        artist = artistName,
        cover = coverUrl,
        duration = duration.toLong(),
        albumTitle = albumTitle,
        trackPath = trackUri
    )
}