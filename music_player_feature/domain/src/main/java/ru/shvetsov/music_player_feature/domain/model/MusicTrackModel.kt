package ru.shvetsov.music_player_feature.domain.model

data class MusicTrackModel(
    val id: Long?,
    val title: String?,
    val artist: String?,
    val cover: String?,
    val albumTitle: String?,
    val duration: Long?,
    val trackPath: String?
)
