package com.weather.di

import android.app.Application
import androidx.work.WorkManager
import com.weather.usecase.NotificationUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object NotificationModule {

    @Provides
    fun provideWorkManager(
        application: Application
    ): WorkManager {
        return WorkManager.getInstance(application)
    }

    @Provides
    fun provideNotificationUseCase(
        workManager: WorkManager
    ): NotificationUseCase {
        return NotificationUseCase(workManager)
    }

}