package ru.shvetsov.music_player_feature.domain.use_cases

import ru.shvetsov.music_player_feature.domain.repository.MusicPlayerRepository
import javax.inject.Inject

class GetTrackFromLocalStorageUseCase @Inject constructor(
    private val musicPlayerRepository: MusicPlayerRepository
) {

    suspend fun invoke(id: Long) = musicPlayerRepository.getTrackFromLocalStorage(id)
}