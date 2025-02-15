package ru.shvetsov.common.model

import ru.shvetsov.common.enums.TrackSource

data class MusicTrack(
    val id: Long?,
    val title: String?,
    val artist: String?,
    val cover: String?,
    val source: TrackSource
)