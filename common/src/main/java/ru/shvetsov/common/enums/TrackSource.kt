package ru.shvetsov.common.enums

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class TrackSource : Parcelable{
    API, LOCAL
}