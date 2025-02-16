package ru.shvetsov.common.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.shvetsov.common.utils.BaseNetworkUseCase
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object CommonModule {

    @Singleton
    @Provides
    fun providesBaseNetworkUseCase(): BaseNetworkUseCase = BaseNetworkUseCase()
}