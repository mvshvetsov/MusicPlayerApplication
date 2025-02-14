package ru.shvetsov.remote_music_feature.data.mappers

import ru.shvetsov.common.model.MusicTrack
import ru.shvetsov.remote_music_feature.data.models.Data

fun Data.toMusicTrackModel(): MusicTrack? {
    val trackId = id ?: return null
    val trackTitle = title ?: return null
    val artistName = artist?.name ?: return null
    val coverUrl = album?.cover ?: ""

    return MusicTrack(
        id = trackId,
        title = trackTitle,
        artist = artistName,
        cover = coverUrl
    )
}