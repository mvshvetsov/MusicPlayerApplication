package ru.shvetsov.musicplayerapplication.navigation

import com.google.gson.Gson
import ru.shvetsov.common.model.MusicTrack
import ru.shvetsov.musicplayerapplication.R
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

sealed class Screen(val route: String, val iconId: Int? = null) {
    data object RemoteMusic : Screen("remote_music", R.drawable.music_note_24)
    data object LocalMusic : Screen("local_music", R.drawable.download_24)
    data object MusicPlayer : Screen("music_player/{selectedTrackIndex}/{trackList}") {
        fun createRoute(selectedTrackIndex: Int, trackList: List<MusicTrack>) =
            "music_player/$selectedTrackIndex/${trackList.toJson()}"
    }
}

fun List<MusicTrack>.toJson(): String {
    val json = Gson().toJson(this)
    return URLEncoder.encode(json, StandardCharsets.UTF_8.toString())
}