package ru.shvetsov.music_player_feature.domain.repository

import ru.shvetsov.music_player_feature.domain.model.MusicTrackModel

interface MusicPlayerRepository {

    suspend fun getTrackFromApi(id: Int): Result<MusicTrackModel>

    suspend fun getTrackFromLocalStorage(id: Long): MusicTrackModel?
}