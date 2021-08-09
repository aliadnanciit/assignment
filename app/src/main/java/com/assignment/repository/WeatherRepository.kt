package com.assignment.repository

import com.assignment.model.ForecastWeather
import com.assignment.model.WeatherResponseData

interface WeatherRepository {

    suspend fun fetchWeatherListByCityName(city: String): com.assignment.model.WeatherResponseData

    suspend fun fetchWeatherByLocation(lat: String, lon: String) : com.assignment.model.WeatherResponseData

    suspend fun fetchWeekForecastWeatherList(city: String, count: Int): com.assignment.model.ForecastWeather

}