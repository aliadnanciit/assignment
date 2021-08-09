package com.assignment.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object FavouritesModule {

    @Provides
    fun providesSharedPreferences(
        application: Application
    ): SharedPreferences {
        return application.getSharedPreferences("FAVOURITES_PREFERENCES", Context.MODE_PRIVATE)
    }
}