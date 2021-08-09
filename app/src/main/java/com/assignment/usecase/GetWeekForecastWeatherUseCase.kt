package com.assignment.usecase

import com.assignment.model.ForecastWeather
import com.assignment.repository.WeatherRepository
import javax.inject.Inject

class GetWeekForecastWeatherUseCase @Inject constructor(
    private val weatherRepository: com.assignment.repository.WeatherRepository
) {

    suspend fun execute(city: String) : com.assignment.model.ForecastWeather {
        return weatherRepository.fetchWeekForecastWeatherList(city, 40)
    }
}