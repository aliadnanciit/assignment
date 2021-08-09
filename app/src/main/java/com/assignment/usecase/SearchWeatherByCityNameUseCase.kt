package com.assignment.usecase

import com.assignment.model.WeatherResponseData
import com.assignment.repository.WeatherRepository
import javax.inject.Inject

class SearchWeatherByCityNameUseCase @Inject constructor(
    private val weatherRepository: com.assignment.repository.WeatherRepository
) {
    suspend fun execute(city: String): com.assignment.model.WeatherResponseData {
        return weatherRepository.fetchWeatherListByCityName(city)
    }
}