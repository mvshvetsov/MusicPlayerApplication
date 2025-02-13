package ru.shvetsov.remote_music_feature.data.mappers

import ru.shvetsov.remote_music_feature.data.models.Data
import ru.shvetsov.remote_music_feature.data.models.MusicTrackDetailsResponse
import ru.shvetsov.common.model.MusicTrack

fun Data.toMusicTrackModel(): MusicTrack? {
    val trackId = id ?: return null
    val trackTitle = title ?: return null
    val artistName = artist?.name ?: return null
    val coverUrl = album?.cover ?: album?.cover_small ?: album?.cover_medium ?: album?.cover_big
    ?: album?.cover_xl ?: ""

    return MusicTrack(
        id = trackId,
        title = trackTitle,
        artist = artistName,
        cover = coverUrl
    )
}

fun MusicTrackDetailsResponse.toMusicTrackModel(): MusicTrack? {
    val trackId = id ?: return null
    val trackTitle = title ?: return null
    val artistName = artist?.name ?: return null
    val coverUrl = album?.cover ?: album?.cover_small ?: album?.cover_medium ?: album?.cover_big
    ?: album?.cover_xl ?: ""

    return MusicTrack(
        id = trackId,
        title = trackTitle,
        artist = artistName,
        cover = coverUrl
    )
}