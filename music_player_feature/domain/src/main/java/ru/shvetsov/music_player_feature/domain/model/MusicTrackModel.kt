package ru.shvetsov.music_player_feature.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import ru.shvetsov.common.enums.TrackSource

@Parcelize
data class MusicTrackModel(
    val id: Long?,
    val title: String?,
    val artist: String?,
    val cover: String?,
    val albumTitle: String?,
    val duration: Long?,
    val trackPath: String?,
    val source: TrackSource
) : Parcelable