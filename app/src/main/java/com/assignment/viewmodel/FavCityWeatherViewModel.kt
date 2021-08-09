package com.assignment.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.assignment.model.FavCityWeatherState
import com.assignment.usecase.AddFavouriteCityUseCase
import com.assignment.usecase.GetFavouritesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavCityWeatherViewModel @Inject constructor(
    private val getFavouritesUseCase: com.assignment.usecase.GetFavouritesUseCase,
    private val addFavouriteCityUseCase: com.assignment.usecase.AddFavouriteCityUseCase
) : ViewModel() {

    val favouritesLiveData = MutableLiveData<com.assignment.model.FavCityWeatherState<Set<String>>>(
        com.assignment.model.FavCityWeatherState.NoFavList)

    fun fetch() {
        viewModelScope.launch {
            val response = getFavouritesUseCase.execute()
            favouritesLiveData.value = if (response.isNotEmpty()) {
                com.assignment.model.FavCityWeatherState.Success(response)
            } else {
                com.assignment.model.FavCityWeatherState.NoFavList
            }
        }
    }

    fun add(cityName: String) {
        viewModelScope.launch {
            addFavouriteCityUseCase.execute(cityName)
        }
    }
}