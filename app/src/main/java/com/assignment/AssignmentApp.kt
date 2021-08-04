package com.assignment

import android.app.Application
import androidx.work.Configuration
import androidx.work.DelegatingWorkerFactory
import com.assignment.base.notificationchannel.AppNotificationChannelManager
import com.weather.notifications.NotificationWorkerFactory
import com.weather.usecase.SearchWeatherByCityNameUseCase
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class AssignmentApp : Application(), Configuration.Provider {

    @Inject
    lateinit var searchWeatherByCityNameUseCase: SearchWeatherByCityNameUseCase
    @Inject
    lateinit var appNotificationChannelManager: AppNotificationChannelManager

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        appNotificationChannelManager.setupNotificationChannel()
    }

    override fun getWorkManagerConfiguration(): Configuration {
        val delegatingWorkerFactory = DelegatingWorkerFactory()
        delegatingWorkerFactory.addFactory(NotificationWorkerFactory(searchWeatherByCityNameUseCase))

        return Configuration.Builder()
            .setMinimumLoggingLevel(android.util.Log.INFO)
            .setWorkerFactory(delegatingWorkerFactory)
            .build()
    }
}