package com.weather.usecase

import com.weather.model.WeatherResponseData
import com.weather.repository.WeatherRepository
import javax.inject.Inject

class FetchWeatherByLocationUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {

    suspend fun execute(lat: String, lon: String) : WeatherResponseData {
        return weatherRepository.fetchWeatherByLocation(lat, lon)
    }
}