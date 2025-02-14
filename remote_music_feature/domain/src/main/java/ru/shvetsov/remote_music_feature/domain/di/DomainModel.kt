package ru.shvetsov.remote_music_feature.domain.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.shvetsov.common.utils.BaseNetworkUseCase
import ru.shvetsov.remote_music_feature.domain.repository.RemoteMusicTracksRepository
import ru.shvetsov.remote_music_feature.domain.use_cases.FetchRemoteMusicTracksUseCase
import ru.shvetsov.remote_music_feature.domain.use_cases.SearchRemoteMusicTracksUseCase
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DomainModel {

    @Singleton
    @Provides
    fun providesFetchRemoteTracksUseCase(
        remoteMusicTracksRepository: RemoteMusicTracksRepository,
        baseNetworkUseCase: BaseNetworkUseCase
    ): FetchRemoteMusicTracksUseCase =
        FetchRemoteMusicTracksUseCase(remoteMusicTracksRepository, baseNetworkUseCase)

    @Singleton
    @Provides
    fun providesSearchRemoteMusicTracksUseCase(
        remoteMusicTracksRepository: RemoteMusicTracksRepository,
        baseNetworkUseCase: BaseNetworkUseCase
    ): SearchRemoteMusicTracksUseCase =
        SearchRemoteMusicTracksUseCase(remoteMusicTracksRepository, baseNetworkUseCase)

    @Singleton
    @Provides
    fun providesBaseNetworkUseCase(): BaseNetworkUseCase = BaseNetworkUseCase()
}