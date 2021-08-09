package com.assignment.model

sealed class UserWeatherState<out T : Any> {
    object Loading: UserWeatherState<Nothing>()

    object PermissionRequired: UserWeatherState<Nothing>()

    data class Success<out T : Any>(val results: T): UserWeatherState<T>()

    data class Error(val exception: Throwable): UserWeatherState<Nothing>()
}