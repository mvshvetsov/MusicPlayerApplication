package ru.shvetsov.music_player_feature.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import ru.shvetsov.common.model.MusicTrack
import ru.shvetsov.music_player_feature.data.models.RemoteMusicTrackResponse

interface MusicPlayerService {

    @GET("track/{id}")
    suspend fun getTrackById(@Path("id") id: Long): Response<RemoteMusicTrackResponse>
}