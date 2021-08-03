package com.weather.model.server

import com.weather.model.Weather

sealed class WeatherStates {

    object Loading : WeatherStates()

    object NoContent : WeatherStates()

    data class Success(val list: List<Weather>) : WeatherStates()

    data class Error(val throwable: Throwable) : WeatherStates()
}
