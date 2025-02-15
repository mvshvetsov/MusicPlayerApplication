package ru.shvetsov.musicplayerapplication.navigation

import com.google.gson.Gson
import ru.shvetsov.common.model.MusicTrack
import ru.shvetsov.musicplayerapplication.R

sealed class Screen(val route: String, val iconId: Int? = null) {
    data object RemoteMusic : Screen("remote_music", R.drawable.music_note_24)
    data object LocalMusic : Screen("local_music", R.drawable.download_24)
    data object MusicPlayer : Screen("music_player/{trackList}") {
        fun createRoute(trackList: List<MusicTrack>) = "music_player/${trackList.toJson()}"
    }
}

fun List<MusicTrack>.toJson(): String {
    return Gson().toJson(this)
}