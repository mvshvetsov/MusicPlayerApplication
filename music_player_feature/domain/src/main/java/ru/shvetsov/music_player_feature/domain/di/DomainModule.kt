package ru.shvetsov.music_player_feature.domain.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.shvetsov.common.utils.BaseNetworkUseCase
import ru.shvetsov.music_player_feature.domain.repository.MusicPlayerRepository
import ru.shvetsov.music_player_feature.domain.use_cases.GetTrackFromApiUseCase
import ru.shvetsov.music_player_feature.domain.use_cases.GetTrackFromLocalStorageUseCase
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
    fun providesBaseNetworkUseCase(): BaseNetworkUseCase = BaseNetworkUseCase()
}