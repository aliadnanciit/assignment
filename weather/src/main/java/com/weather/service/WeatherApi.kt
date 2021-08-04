package com.weather.service

import com.weather.model.ForecastWeather
import com.weather.model.WeatherResponseData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("weather")
    suspend fun getWeatherByCity(
        @Query("q") city: String,
        @Query("appid") apiKey: String
    ): Response<WeatherResponseData>


    @GET("weather")
    suspend fun getWeatherByLocation(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("appid") apiKey: String
    ): Response<WeatherResponseData>

    @GET("forecast")
    suspend fun getWeekForecastWeatherByCity(
        @Query("q") city: String,
        @Query("appid") apiKey: String,
        @Query("units") units: String,
        @Query("cnt") count: Int,
    ): Response<ForecastWeather>
}