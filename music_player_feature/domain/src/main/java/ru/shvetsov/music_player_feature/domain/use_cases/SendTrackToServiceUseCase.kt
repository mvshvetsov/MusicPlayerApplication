package ru.shvetsov.music_player_feature.domain.use_cases

import ru.shvetsov.music_player_feature.domain.model.MusicTrackModel
import ru.shvetsov.music_player_feature.domain.repository.MusicPlayerRepository
import javax.inject.Inject

class SendTrackToServiceUseCase @Inject constructor(
    private val musicPlayerRepository: MusicPlayerRepository
) {

    fun invoke(musicTrackModel: MusicTrackModel) = musicPlayerRepository.sendTrackToService(musicTrackModel)
}