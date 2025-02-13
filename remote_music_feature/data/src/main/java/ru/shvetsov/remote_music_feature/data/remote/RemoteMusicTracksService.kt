package ru.shvetsov.remote_music_feature.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.shvetsov.remote_music_feature.data.models.MusicTrackDetailsResponse
import ru.shvetsov.remote_music_feature.data.models.MusicTrackResponse
import ru.shvetsov.remote_music_feature.data.models.MusicTrackSearchResponse

interface RemoteMusicTracksService {

    @GET("chart")
    suspend fun fetchMusicTracks(): Response<MusicTrackResponse>

    @GET("search")
    suspend fun searchMusicTracks(@Query("q") query: String): Response<MusicTrackSearchResponse>

    @GET("track/{id}")
    suspend fun getMusicTrackById(@Path("id") id: Long): Response<MusicTrackDetailsResponse>
}