package com.assignment.model

sealed class UIState {

    object Loading: UIState()

    data class Success(val data: Any): UIState()

    data class Error(val exception: Throwable): UIState()
}