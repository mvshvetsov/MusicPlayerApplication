package ru.shvetsov.local_music_feture.data.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.shvetsov.local_music_feature.domain.repository.LocalMusicTracksRepository
import ru.shvetsov.local_music_feture.data.repository.LocalMusicTracksRepositoryImpl
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DataModule {

    @Singleton
    @Provides
    fun providesLocalMusicMusicRepository(@ApplicationContext context: Context): LocalMusicTracksRepository =
        LocalMusicTracksRepositoryImpl(context)
}