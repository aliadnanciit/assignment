package com.weather.notifications

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.weather.usecase.SearchWeatherByCityNameUseCase
import javax.inject.Inject

class NotificationWorkerFactory @Inject constructor(
    private val searchWeatherByCityNameUseCase: SearchWeatherByCityNameUseCase
) : WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        return when (workerClassName) {
            DailyNotificationWorker::class.java.name ->
                DailyNotificationWorker(
                    appContext,
                    workerParameters,
                    searchWeatherByCityNameUseCase
                )
            else ->
                // Return null, so that the base class can delegate to the default WorkerFactory.
                null
        }

    }
}