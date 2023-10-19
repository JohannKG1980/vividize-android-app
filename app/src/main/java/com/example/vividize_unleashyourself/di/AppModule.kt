package com.example.vividize_unleashyourself.di

import android.app.Application
import android.media.MediaPlayer
import com.example.vividize_unleashyourself.data.remote.QuotesApi
import com.example.vividize_unleashyourself.data.AppRepository
import com.example.vividize_unleashyourself.data.model.MyObjectBox
import com.example.vividize_unleashyourself.data.remote.QuotesApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.objectbox.BoxStore
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideBoxStore(application: Application): BoxStore {
        return MyObjectBox.builder().androidContext(application).build()
    }


    @Provides
    @Singleton
    fun provideQuotesApiService(): QuotesApiService = QuotesApi.retrofitService

    @Provides
    @Singleton
    fun provideAppRepository(apiService: QuotesApiService, boxStore: BoxStore): AppRepository = AppRepository(apiService, boxStore)


    @Provides
    @Singleton
    fun provideMediaPlayer(): MediaPlayer {
        return MediaPlayer()
    }
}
