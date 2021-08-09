package com.assignment.usecase

import com.assignment.model.ForecastWeather
import com.assignment.repository.WeatherRepository
import javax.inject.Inject

class AddToHistoryUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {

    suspend fun execute(city: String) : ForecastWeather {
        return weatherRepository.fetchWeekForecastWeatherList(city, 40)
    }
}