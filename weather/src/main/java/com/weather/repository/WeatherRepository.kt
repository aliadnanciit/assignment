package com.weather.repository

import com.weather.model.ForecastWeather
import com.weather.model.WeatherResponseData

interface WeatherRepository {

    suspend fun fetchWeatherListByCityName(city: String): WeatherResponseData

    suspend fun fetchWeatherByLocation(lat: String, lon: String) : WeatherResponseData

    suspend fun fetchWeekForecastWeatherList(city: String, count: Int): ForecastWeather

}