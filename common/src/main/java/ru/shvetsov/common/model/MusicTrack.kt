package ru.shvetsov.common.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import ru.shvetsov.common.enums.TrackSource

@Parcelize
data class MusicTrack(
    val id: Long?,
    val title: String?,
    val artist: String?,
    val cover: String?,
    val source: TrackSource
) : Parcelable