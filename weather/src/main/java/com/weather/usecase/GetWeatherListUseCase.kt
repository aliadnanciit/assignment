package com.weather.usecase

import com.weather.model.Weather
import com.weather.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetWeatherListUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {

    suspend fun execute(): Flow<List<Weather>> {
        return weatherRepository.getWeatherList()
    }
}