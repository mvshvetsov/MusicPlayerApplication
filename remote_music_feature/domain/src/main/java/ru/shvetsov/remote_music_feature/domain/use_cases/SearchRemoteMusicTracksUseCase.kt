package ru.shvetsov.remote_music_feature.domain.use_cases

import ru.shvetsov.common.utils.BaseNetworkUseCase
import ru.shvetsov.remote_music_feature.domain.repository.RemoteMusicTracksRepository
import javax.inject.Inject

class SearchRemoteMusicTracksUseCase @Inject constructor(
    private val remoteMusicTracksRepository: RemoteMusicTracksRepository,
    private val baseNetworkUseCase: BaseNetworkUseCase
) {

    fun invoke(query: String) =
        baseNetworkUseCase.handleRequest { remoteMusicTracksRepository.searchMusicTracks(query) }
}