package com.weather.usecase

import com.weather.model.ForecastWeather
import com.weather.repository.WeatherRepository
import javax.inject.Inject

class GetWeakForecastWeatherUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {

    suspend fun execute(city: String) : ForecastWeather {
        return weatherRepository.fetchWeekForecastWeatherList(city, 40)
    }
}