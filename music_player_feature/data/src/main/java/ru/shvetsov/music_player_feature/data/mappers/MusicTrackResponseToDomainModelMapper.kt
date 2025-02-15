package ru.shvetsov.music_player_feature.data.mappers

import ru.shvetsov.music_player_feature.data.models.RemoteMusicTrackResponse
import ru.shvetsov.music_player_feature.domain.model.MusicTrackModel

fun RemoteMusicTrackResponse.toMusicTrackModel(): MusicTrackModel? {
    val id = id ?: return null
    val title = title ?: return null
    val artist = artist?.name ?: return null
    val cover = album?.cover ?: return null
    val albumTitle = album.title ?: return null
    val duration = duration ?: return null
    val trackPath = preview ?: return null

    return MusicTrackModel(
        id = id.toLong(),
        title = title,
        artist = artist,
        cover = cover,
        albumTitle = albumTitle,
        duration = duration.toLong(),
        trackPath = trackPath
    )
}