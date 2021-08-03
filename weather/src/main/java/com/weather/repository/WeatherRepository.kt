package com.weather.repository

import com.weather.model.Weather
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {

    suspend fun getWeatherList(): Flow<List<Weather>>

    suspend fun fetchWeatherList()
}