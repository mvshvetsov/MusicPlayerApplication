package ru.shvetsov.local_music_feature.domain.use_cases

import ru.shvetsov.common.model.MusicTrack
import ru.shvetsov.local_music_feature.domain.repository.LocalMusicTracksRepository
import javax.inject.Inject

class FetchLocalMusicTracksUseCase @Inject constructor(
    private val localMusicTracksRepository: LocalMusicTracksRepository
) {
    fun invoke(): List<MusicTrack> = localMusicTracksRepository.fetchMusicTracks()
}