package ru.shvetsov.music_player_feature.domain.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.shvetsov.common.utils.BaseNetworkUseCase
import ru.shvetsov.music_player_feature.domain.repository.MusicPlayerRepository
import ru.shvetsov.music_player_feature.domain.use_cases.GetTrackFromApiUseCase
import ru.shvetsov.music_player_feature.domain.use_cases.GetTrackFromLocalStorageUseCase
import ru.shvetsov.music_player_feature.domain.use_cases.PauseTrackUseCase
import ru.shvetsov.music_player_feature.domain.use_cases.PlayNextTrackUseCase
import ru.shvetsov.music_player_feature.domain.use_cases.PlayPreviousTrackUseCase
import ru.shvetsov.music_player_feature.domain.use_cases.PlayTrackUseCase
import ru.shvetsov.music_player_feature.domain.use_cases.ResumeTrackUseCase
import ru.shvetsov.music_player_feature.domain.use_cases.SeekToUseCase
import ru.shvetsov.music_player_feature.domain.use_cases.SendTrackToServiceUseCase
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DomainModule {

    @Singleton
    @Provides
    fun providesGetTrackFromApiUseCase(
        musicPlayerRepository: MusicPlayerRepository,
        baseNetworkUseCase: BaseNetworkUseCase
    ) =
        GetTrackFromApiUseCase(musicPlayerRepository, baseNetworkUseCase)

    @Singleton
    @Provides
    fun providesGetTrackFromLocalStorageUseCase(musicPlayerRepository: MusicPlayerRepository) =
        GetTrackFromLocalStorageUseCase(musicPlayerRepository)

    @Singleton
    @Provides
    fun providesPauseTrackUseCase(musicPlayerRepository: MusicPlayerRepository) =
        PauseTrackUseCase(musicPlayerRepository)

    @Singleton
    @Provides
    fun providesPlayNextTrackUseCase(musicPlayerRepository: MusicPlayerRepository) =
        PlayNextTrackUseCase(musicPlayerRepository)

    @Singleton
    @Provides
    fun providesPlayPreviousTrackUseCase(musicPlayerRepository: MusicPlayerRepository) =
        PlayPreviousTrackUseCase(musicPlayerRepository)

    @Singleton
    @Provides
    fun providesPlayTrackUseCase(musicPlayerRepository: MusicPlayerRepository) =
        PlayTrackUseCase(musicPlayerRepository)

    @Singleton
    @Provides
    fun providesResumeTrackUseCase(musicPlayerRepository: MusicPlayerRepository) =
        ResumeTrackUseCase(musicPlayerRepository)

    @Singleton
    @Provides
    fun providesSeekToUseCase(musicPlayerRepository: MusicPlayerRepository) =
        SeekToUseCase(musicPlayerRepository)

    @Singleton
    @Provides
    fun providesSendTrackToServiceUseCase(musicPlayerRepository: MusicPlayerRepository) =
        SendTrackToServiceUseCase(musicPlayerRepository)
}