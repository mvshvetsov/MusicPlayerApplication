package ru.shvetsov.local_music_feature.domain.use_cases

import ru.shvetsov.local_music_feature.domain.repository.LocalMusicTracksRepository
import javax.inject.Inject

class SearchLocalMusicTracksUseCase @Inject constructor(
    private val localMusicTracksRepository: LocalMusicTracksRepository
) {

    fun invoke(query: String) = localMusicTracksRepository.searchMusicTracks(query)
}