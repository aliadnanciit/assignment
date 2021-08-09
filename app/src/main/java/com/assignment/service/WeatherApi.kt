package com.assignment.service

import com.assignment.model.ForecastWeather
import com.assignment.model.WeatherResponseData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("weather")
    suspend fun getWeatherByCity(
        @Query("q") city: String
    ): Response<com.assignment.model.WeatherResponseData>


    @GET("weather")
    suspend fun getWeatherByLocation(
        @Query("lat") lat: String,
        @Query("lon") lon: String
    ): Response<com.assignment.model.WeatherResponseData>

    @GET("forecast")
    suspend fun getWeekForecastWeatherByCity(
        @Query("q") city: String,
        @Query("cnt") count: Int,
    ): Response<com.assignment.model.ForecastWeather>
}