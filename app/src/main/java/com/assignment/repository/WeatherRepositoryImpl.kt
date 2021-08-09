package com.assignment.repository

import com.assignment.common.safeApiCall
import com.assignment.database.WeatherDao
import com.assignment.exception.ApiNetworkException
import com.assignment.model.ForecastWeather
import com.assignment.model.WeatherResponseData
import com.assignment.service.WeatherApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named

class WeatherRepositoryImpl @Inject constructor(
    private val weatherApi: WeatherApi,
    private val weatherDao: com.assignment.database.WeatherDao,
    @Named("IO_DISPATCHER") private val ioDispatcher: CoroutineDispatcher
) : WeatherRepository {

    override suspend fun fetchWeatherListByCityName(city: String): com.assignment.model.WeatherResponseData {
        return withContext(ioDispatcher) {
            val response = com.assignment.common.safeApiCall {
                weatherApi.getWeatherByCity(city)
            }
            if (response.isSuccessful) {
                response.body()!!
            } else {
                throw com.assignment.exception.ApiNetworkException("Fail to get weather due to network error")
            }
        }
    }

    override suspend fun fetchWeatherByLocation(lat: String, lon: String) : com.assignment.model.WeatherResponseData {
        return withContext(ioDispatcher) {
            val response = com.assignment.common.safeApiCall {
                weatherApi.getWeatherByLocation(lat, lon)
            }
            if (response.isSuccessful) {
                response.body()!!
            } else {
                throw com.assignment.exception.ApiNetworkException("Fail to get weather due to network error")
            }
        }
    }

    override suspend fun fetchWeekForecastWeatherList(
        city: String,
        count: Int
    ) : com.assignment.model.ForecastWeather {
        return withContext(ioDispatcher) {
            val response = com.assignment.common.safeApiCall {
                weatherApi.getWeekForecastWeatherByCity(city, count)
            }
            if (response.isSuccessful) {
                response.body()!!
            } else {
                throw com.assignment.exception.ApiNetworkException("Fail to get week weather due to network error")
            }
        }
    }
}