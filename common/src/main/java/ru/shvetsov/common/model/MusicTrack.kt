package ru.shvetsov.common.model

data class MusicTrack(
    val id: Long?,
    val title: String?,
    val artist: String?,
    val cover: String?,
    val duration: Long?,
    val albumTitle: String?,
    val trackPath: String?
)
