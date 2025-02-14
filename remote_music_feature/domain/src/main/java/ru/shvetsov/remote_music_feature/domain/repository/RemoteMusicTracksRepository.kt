package ru.shvetsov.remote_music_feature.domain.repository

import ru.shvetsov.common.model.MusicTrack

interface RemoteMusicTracksRepository {

    suspend fun fetchMusicTracks(): Result<List<MusicTrack>>

    suspend fun searchMusicTracks(query: String): Result<List<MusicTrack>>
}