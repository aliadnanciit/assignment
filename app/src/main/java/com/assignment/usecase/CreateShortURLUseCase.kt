package com.assignment.usecase

import com.assignment.model.WeatherResponseData
import com.assignment.repository.WeatherRepository
import javax.inject.Inject

class CreateShortURLUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {
    suspend fun create(city: String): WeatherResponseData {
        return weatherRepository.fetchWeatherListByCityName(city)
    }
}