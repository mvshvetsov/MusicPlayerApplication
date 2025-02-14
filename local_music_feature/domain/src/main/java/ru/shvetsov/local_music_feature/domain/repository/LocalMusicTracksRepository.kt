package ru.shvetsov.local_music_feature.domain.repository

import ru.shvetsov.common.model.MusicTrack

interface LocalMusicTracksRepository {

    fun fetchMusicTracks(): List<MusicTrack>

    fun searchMusicTracks(query: String): List<MusicTrack>
}