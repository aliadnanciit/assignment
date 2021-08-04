package com.weather.usecase

import androidx.work.*
import com.assignment.base.RESCHEDULE_HOUR
import com.assignment.base.RESCHEDULE_MIN
import com.assignment.base.RESCHEDULE_SEC
import com.weather.notifications.API_KEY
import com.weather.notifications.CITY_NAME
import com.weather.notifications.DailyNotificationWorker
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

private const val WORK_NAME = "notification"

class WeatherNotificationUseCase @Inject constructor(
    private val workManager: WorkManager
) {
    fun scheduleNotification(city: String, apiKey: String) {
        val data = Data.Builder()
            .putString(CITY_NAME, city)
            .putString(API_KEY, apiKey)
            .build()

        val constraints = Constraints.Builder()
            .setRequiresBatteryNotLow(true)
            .build()

        val currentDate = Calendar.getInstance()
        val dueDate = Calendar.getInstance()
        dueDate.set(Calendar.HOUR_OF_DAY, RESCHEDULE_HOUR) // at 6 am
        dueDate.set(Calendar.MINUTE, RESCHEDULE_MIN)
        dueDate.set(Calendar.SECOND, RESCHEDULE_SEC)

        val timeDiff = dueDate.timeInMillis - currentDate.timeInMillis
        val dailyWorkRequest = OneTimeWorkRequestBuilder<DailyNotificationWorker>()
            .setInputData(data)
            .setConstraints(constraints)
            .setInitialDelay(timeDiff, TimeUnit.MILLISECONDS)
            .addTag(WORK_NAME)
            .build()

        workManager.enqueueUniqueWork(WORK_NAME, ExistingWorkPolicy.REPLACE, dailyWorkRequest)
    }
}
