package com.example.vividize_unleashyourself.di

import android.media.MediaPlayer
import com.example.vividize_unleashyourself.data.remote.QuotesApi
import com.example.vividize_unleashyourself.data.AppRepository
import com.example.vividize_unleashyourself.data.remote.QuotesApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideQuotesApiService(): QuotesApiService = QuotesApi.retrofitService

    @Provides
    @Singleton
    fun provideAppRepository(apiService: QuotesApiService): AppRepository = AppRepository(apiService)


    @Provides
    @Singleton
    fun provideMediaPlayer(): MediaPlayer {
        return MediaPlayer()
    }
}
