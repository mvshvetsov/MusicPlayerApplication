package ru.shvetsov.remote_music_feature.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import ru.shvetsov.remote_music_feature.data.remote.RemoteMusicTracksService
import ru.shvetsov.remote_music_feature.data.repository.RemoteMusicTracksRepositoryImpl
import ru.shvetsov.remote_music_feature.domain.repository.RemoteMusicTracksRepository
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DataModule {

    @Provides
    @Singleton
    fun providesMusicTrackService(retrofit: Retrofit): RemoteMusicTracksService =
        retrofit.create(RemoteMusicTracksService::class.java)

    @Provides
    @Singleton
    fun providesMusicTrackRepository(remoteMusicTrackService: RemoteMusicTracksService): RemoteMusicTracksRepository =
        RemoteMusicTracksRepositoryImpl(remoteMusicTrackService)
}