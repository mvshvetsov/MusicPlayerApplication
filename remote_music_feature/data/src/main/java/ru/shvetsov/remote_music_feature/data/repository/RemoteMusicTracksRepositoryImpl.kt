package ru.shvetsov.remote_music_feature.data.repository

import ru.shvetsov.common.utils.BaseApiResponse
import ru.shvetsov.remote_music_feature.data.mappers.toMusicTrackModel
import ru.shvetsov.remote_music_feature.data.remote.RemoteMusicTracksService
import ru.shvetsov.remote_music_feature.domain.repository.RemoteMusicTracksRepository
import ru.shvetsov.track_list_common.model.MusicTrack
import javax.inject.Inject

class RemoteMusicTracksRepositoryImpl @Inject constructor(
    private val remoteMusicTracksService: RemoteMusicTracksService
) : BaseApiResponse(), RemoteMusicTracksRepository {

    override suspend fun fetchMusicTracks(): Result<List<MusicTrack>> {
        return handleApiCall(
            api = { remoteMusicTracksService.fetchMusicTracks() },
            mapper = { response ->
                response.tracks?.data?.mapNotNull { it.toMusicTrackModel() } ?: emptyList()
            }
        )
    }

    override suspend fun searchMusicTracks(query: String): Result<List<MusicTrack>> {
        return handleApiCall(
            api = { remoteMusicTracksService.searchMusicTracks(query) },
            mapper = { response ->
                response.tracks?.data?.mapNotNull { it.toMusicTrackModel() } ?: emptyList()
            }
        )
    }

    override suspend fun getMusicTrackById(id: Long): Result<MusicTrack> {
        return handleApiCall(
            api = { remoteMusicTracksService.getMusicTrackById(id) },
            mapper = { response ->
                response.toMusicTrackModel() ?: throw Exception("Music track not found")
            }
        )
    }
}