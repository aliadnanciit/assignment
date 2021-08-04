package com.weather.repository

import com.weather.model.ForecastWeather
import com.weather.model.WeatherResponseData

interface WeatherRepository {

    suspend fun fetchWeatherList(city: String, apiKey: String): WeatherResponseData

    suspend fun fetchWeatherByLocation(lat: String, lon: String, apiKey: String) : WeatherResponseData

    suspend fun fetchWeekForecastWeatherList(
        city: String,
        apiKey: String,
        units: String = "metric",
        count: Int
    ): ForecastWeather

}