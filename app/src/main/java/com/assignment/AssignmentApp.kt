package com.assignment

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.work.Configuration
import androidx.work.DelegatingWorkerFactory
import com.assignment.base.CHANNEL_ID
import com.weather.notifications.NotificationWorkerFactory
import com.weather.usecase.SearchWeatherByCityNameUseCase
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

private const val CHANNEL_NAME = "notification channel"
@HiltAndroidApp
class AssignmentApp : Application(), Configuration.Provider {

    @Inject
    lateinit var searchWeatherByCityNameUseCase: SearchWeatherByCityNameUseCase

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val mChannel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance)
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(mChannel)
        }
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