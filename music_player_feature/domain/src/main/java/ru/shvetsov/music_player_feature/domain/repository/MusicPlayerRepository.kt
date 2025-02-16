package ru.shvetsov.music_player_feature.domain.repository

import ru.shvetsov.music_player_feature.domain.model.MusicTrackModel

interface MusicPlayerRepository {

    suspend fun getTrackFromApi(id: Long): Result<MusicTrackModel>

    fun getTrackFromLocalStorage(id: Long): MusicTrackModel?

    fun playTrack(uri: String)

    fun pauseTrack()

    fun resumeTrack()

    fun seekTo(position: Long)

    fun playNextTrack()

    fun playPreviousTrack()

    fun sendTrackToService(track: MusicTrackModel)
}