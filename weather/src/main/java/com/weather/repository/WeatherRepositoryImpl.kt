package com.weather.repository

import com.weather.common.safeApiCall
import com.weather.database.WeatherDao
import com.weather.exception.ApiNetworkException
import com.weather.model.ForecastWeather
import com.weather.model.WeatherResponseData
import com.weather.service.WeatherApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named

class WeatherRepositoryImpl @Inject constructor(
    private val weatherApi: WeatherApi,
    private val weatherDao: WeatherDao,
    @Named("IO_DISPATCHER") private val ioDispatcher: CoroutineDispatcher
) : WeatherRepository {

    override suspend fun fetchWeatherList(city: String, apiKey: String): WeatherResponseData {
        return withContext(ioDispatcher) {
            val response = safeApiCall {
                weatherApi.getWeatherByCity(city, apiKey)
            }
            if (response.isSuccessful) {
                response.body()!!
            } else {
                throw ApiNetworkException("Fail to get weather due to network error")
            }
        }
    }

    override suspend fun fetchWeatherByLocation(lat: String, lon: String, apiKey: String) : WeatherResponseData {
        return withContext(ioDispatcher) {
            val response = safeApiCall {
                weatherApi.getWeatherByLocation(lat, lon, apiKey)
            }
            if (response.isSuccessful) {
                response.body()!!
            } else {
                throw ApiNetworkException("Fail to get weather due to network error")
            }
        }
    }

    override suspend fun fetchWeekForecastWeatherList(
        city: String,
        apiKey: String,
        units: String,
        count: Int
    ) : ForecastWeather {
        return withContext(ioDispatcher) {
            val response = safeApiCall {
                weatherApi.getWeekForecastWeatherByCity(city, apiKey, units, count)
            }
            if (response.isSuccessful) {
                response.body()!!
            } else {
                throw ApiNetworkException("Fail to get week weather due to network error")
            }
        }
    }
}