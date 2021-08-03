package com.weather.usecase

import com.weather.repository.WeatherRepository
import javax.inject.Inject

class FetchWeatherListUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {

    suspend fun execute() {
        return weatherRepository.fetchWeatherList()
    }
}