package com.weather.usecase

import com.weather.model.WeatherResponseData
import com.weather.repository.WeatherRepository
import javax.inject.Inject

class SearchWeatherByCityNameUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {

    suspend fun execute(city: String): WeatherResponseData {
        return weatherRepository.fetchWeatherList(city)
    }
}