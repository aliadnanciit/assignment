package com.assignment.model

sealed class UIState<out T : Any> {

    object Loading: UIState<Nothing>()

    data class Success<out T : Any>(val results: T): UIState<T>()

    data class Error(val exception: Throwable): UIState<Nothing>()
}