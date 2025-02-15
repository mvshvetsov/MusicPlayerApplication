package ru.shvetsov.music_player_feature.data.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import ru.shvetsov.music_player_feature.data.remote.MusicPlayerService
import ru.shvetsov.music_player_feature.data.repository.MusicPlayerRepositoryImpl
import ru.shvetsov.music_player_feature.domain.repository.MusicPlayerRepository
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DataModule {

    @Singleton
    @Provides
    fun providesMusicPlayerService(retrofit: Retrofit): MusicPlayerService =
        retrofit.create(MusicPlayerService::class.java)

    @Singleton
    @Provides
    fun providesMusicPlayerRepository(
        musicPlayerService: MusicPlayerService,
        @ApplicationContext context: Context
    ): MusicPlayerRepository =
        MusicPlayerRepositoryImpl(musicPlayerService, context)
}