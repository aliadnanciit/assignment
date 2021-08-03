package com.weather.model

sealed class FavCityWeatherState<out T : Any> {

    object NoFavList: FavCityWeatherState<Nothing>()

    data class Success<out T : Any>(val results: T): FavCityWeatherState<T>()
}