package com.assignment.usecase

import com.assignment.model.WeatherResponseData
import com.assignment.repository.WeatherRepository
import javax.inject.Inject

class FetchWeatherByLocationUseCase @Inject constructor(
    private val weatherRepository: com.assignment.repository.WeatherRepository
) {

    suspend fun execute(lat: String, lon: String) : com.assignment.model.WeatherResponseData {
        return weatherRepository.fetchWeatherByLocation(lat, lon)
    }
}