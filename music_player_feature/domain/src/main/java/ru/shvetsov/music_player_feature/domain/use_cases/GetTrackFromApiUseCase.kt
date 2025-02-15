package ru.shvetsov.music_player_feature.domain.use_cases

import ru.shvetsov.common.utils.BaseNetworkUseCase
import ru.shvetsov.music_player_feature.domain.repository.MusicPlayerRepository
import javax.inject.Inject

class GetTrackFromApiUseCase @Inject constructor(
    private val musicPlayerRepository: MusicPlayerRepository,
    private val baseNetworkUseCase: BaseNetworkUseCase
) {

    fun invoke(id: Int) = baseNetworkUseCase.handleRequest { musicPlayerRepository.getTrackFromApi(id) }
}