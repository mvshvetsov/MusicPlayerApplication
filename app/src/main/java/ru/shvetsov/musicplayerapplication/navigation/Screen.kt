package ru.shvetsov.musicplayerapplication.navigation

import ru.shvetsov.musicplayerapplication.R

sealed class Screen(val route: String, val iconId: Int) {
    data object RemoteMusic : Screen("remote_music", R.drawable.music_note_24)
    data object LocalMusic : Screen("local_music", R.drawable.download_24)
}