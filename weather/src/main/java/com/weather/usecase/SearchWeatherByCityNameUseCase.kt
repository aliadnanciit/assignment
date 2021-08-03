package com.weather.usecase

import com.weather.repository.WeatherRepository
import javax.inject.Inject

class SearchWeatherByCityNameUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {

    suspend fun execute(city: String, apiKey: String) {
        return weatherRepository.fetchWeatherList(city, apiKey)
    }
}