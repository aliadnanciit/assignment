package com.assignment.di

import android.content.Context
import androidx.room.Room
import com.assignment.database.AppDatabase
import com.assignment.database.HistoryDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
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
    fun provideMovieDao(appDatabase: AppDatabase): HistoryDao {
        return appDatabase.historyDao()
    }
}