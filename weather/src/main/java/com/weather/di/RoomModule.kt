package com.weather.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import com.weather.database.AppDatabase
import com.weather.database.WeatherDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java, "AppDatabase"
        ).build()
    }

    @Provides
    fun provideMovieDao(appDatabase: AppDatabase): WeatherDao {
        return appDatabase.weatherDao()
    }
}