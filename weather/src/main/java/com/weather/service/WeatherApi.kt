package com.weather.service

import com.weather.model.ForecastWeather
import com.weather.model.WeatherResponseData
import com.weather.model.server.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("cms/test/campaigns.json")
    suspend fun getCampaigns(): Response<WeatherResponse>

    @GET("weather")
    suspend fun getWeatherByCity(
        @Query("q") city: String,
        @Query("appid") apiKey: String
    ): Response<WeatherResponseData>

    //https://api.openweathermap.org/data/2.5/weather?lat=37.421851&lon=-122.0841818&appid=710c6ff29ebad2f6059e31dd6c25923a
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