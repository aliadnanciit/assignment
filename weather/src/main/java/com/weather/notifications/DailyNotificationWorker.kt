package com.weather.notifications

import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.*
import com.assignment.base.CHANNEL_ID
import com.assignment.base.RESCHEDULE_HOUR
import com.assignment.base.RESCHEDULE_MIN
import com.assignment.base.RESCHEDULE_SEC
import com.weather.R
import com.weather.usecase.SearchWeatherByCityNameUseCase
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.random.Random

internal const val CITY_NAME = "city_name"
internal const val API_KEY = "api_key"
internal const val WORK_NAME = "notification"

class DailyNotificationWorker(
    val context: Context,
    workerParameters: WorkerParameters,
    private val searchWeatherByCityNameUseCase: SearchWeatherByCityNameUseCase
) : CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        val city = inputData.getString(CITY_NAME)
        val apiKey = inputData.getString(API_KEY)
        if (city.isNullOrBlank() || apiKey.isNullOrBlank()) {
            return Result.failure()
        }
        val notificationTitle: String
        val notificationText: String?
        searchWeatherByCityNameUseCase.execute(
            inputData.getString(CITY_NAME)!!, inputData.getString(
                API_KEY
            )!!
        ).apply {
            notificationTitle = name
            notificationText = weather?.first()?.description
        }

        reScheduleNotification(context, city, apiKey)

        return notificationText?.let {
            val builder = NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle(notificationTitle)
                .setContentText(it)
                .setSmallIcon(R.drawable.ic_weather_notification)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            with(NotificationManagerCompat.from(context)) {
                notify(Random.nextInt(), builder.build())
            }
            Result.success()
        } ?: run { Result.failure() }
    }

    private fun reScheduleNotification(context: Context, city: String, apiKey: String) {
        val data = Data.Builder()
            .putString(CITY_NAME, city)
            .putString(API_KEY, apiKey)
            .build()

        val constraints = Constraints.Builder()
            .setRequiresBatteryNotLow(true)
            .build()

        val currentDate = Calendar.getInstance()
        val dueDate = Calendar.getInstance()
        dueDate.set(Calendar.HOUR_OF_DAY, RESCHEDULE_HOUR)
        dueDate.set(Calendar.MINUTE, RESCHEDULE_MIN)
        dueDate.set(Calendar.SECOND, RESCHEDULE_SEC)

        dueDate.add(Calendar.HOUR_OF_DAY, 24) // schedule for next day

        val timeDiff = dueDate.timeInMillis - currentDate.timeInMillis
        val dailyWorkRequest = OneTimeWorkRequestBuilder<DailyNotificationWorker>()
            .setInputData(data)
            .setConstraints(constraints)
            .setInitialDelay(timeDiff, TimeUnit.MILLISECONDS)
            .addTag(WORK_NAME)
            .build()

        WorkManager.getInstance(context).enqueueUniqueWork(WORK_NAME, ExistingWorkPolicy.REPLACE, dailyWorkRequest)
    }
}