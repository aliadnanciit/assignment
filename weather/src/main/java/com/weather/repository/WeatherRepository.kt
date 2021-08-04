package com.weather.repository

import com.weather.model.ForecastWeather
import com.weather.model.Weather
import com.weather.model.WeatherResponseData
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {

    suspend fun getWeatherList(): Flow<List<Weather>>

    suspend fun fetchWeatherList(city: String, apiKey: String): WeatherResponseData

    suspend fun fetchWeatherByLocation(lat: String, lon: String, apiKey: String) : WeatherResponseData

    suspend fun fetchWeekForecastWeatherList(
        city: String,
        apiKey: String,
        units: String = "metric",
        count: Int
    ): ForecastWeather

}