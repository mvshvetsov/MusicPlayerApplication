package ru.shvetsov.remote_music_feature.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.shvetsov.remote_music_feature.data.remote.RemoteMusicTracksService
import ru.shvetsov.remote_music_feature.data.repository.RemoteMusicTracksRepositoryImpl
import ru.shvetsov.remote_music_feature.domain.repository.RemoteMusicTracksRepository
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DataModule {

    private const val BASE_URL = "https://api.deezer.com/"

    @Provides
    @Singleton
    fun providesLoggingInterceptor() = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Provides
    @Singleton
    fun providesOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor) =
        OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()

    @Provides
    @Singleton
    fun providesRetrofitClient(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun providesMusicTrackService(retrofit: Retrofit): RemoteMusicTracksService =
        retrofit.create(RemoteMusicTracksService::class.java)

    @Provides
    @Singleton
    fun providesMusicTrackRepository(remoteMusicTrackService: RemoteMusicTracksService): RemoteMusicTracksRepository =
        RemoteMusicTracksRepositoryImpl(remoteMusicTrackService)
}