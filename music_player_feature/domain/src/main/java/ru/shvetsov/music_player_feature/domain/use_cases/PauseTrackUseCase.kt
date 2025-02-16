package ru.shvetsov.music_player_feature.domain.use_cases

import ru.shvetsov.music_player_feature.domain.repository.MusicPlayerRepository
import javax.inject.Inject

class PauseTrackUseCase @Inject constructor(
    private val musicPlayerRepository: MusicPlayerRepository
) {

    fun invoke() = musicPlayerRepository.pauseTrack()
}