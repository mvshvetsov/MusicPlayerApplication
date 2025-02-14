package ru.shvetsov.local_music_feature.domain.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.shvetsov.local_music_feature.domain.repository.LocalMusicTracksRepository
import ru.shvetsov.local_music_feature.domain.use_cases.FetchLocalMusicTracksUseCase
import ru.shvetsov.local_music_feature.domain.use_cases.SearchLocalMusicTracksUseCase
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DomainModule {

    @Singleton
    @Provides
    fun providesFetchLocalMusicTracksUseCase(localMusicTracksRepository: LocalMusicTracksRepository): FetchLocalMusicTracksUseCase =
        FetchLocalMusicTracksUseCase(localMusicTracksRepository)

    @Singleton
    @Provides
    fun providesSearchLocalMusicTracksUseCase(localMusicTracksRepository: LocalMusicTracksRepository): SearchLocalMusicTracksUseCase =
        SearchLocalMusicTracksUseCase(localMusicTracksRepository)
}