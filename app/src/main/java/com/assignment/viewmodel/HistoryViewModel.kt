package com.assignment.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.assignment.usecase.AddToHistoryUseCase
import com.assignment.usecase.GetHistoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val getHistoryUseCase: GetHistoryUseCase,
    private val addToHistoryUseCase: AddToHistoryUseCase
) : ViewModel() {

    val favouritesLiveData = MutableLiveData<com.assignment.model.FavCityWeatherState<Set<String>>>(
        com.assignment.model.FavCityWeatherState.NoFavList)

    fun getHistory() {
        viewModelScope.launch {
        }
    }

    fun addHistory(cityName: String) {
        viewModelScope.launch {

        }
    }
}