package com.weather.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weather.model.FavCityWeatherState
import com.weather.usecase.AddFavouriteCityUseCase
import com.weather.usecase.GetFavouritesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavCityWeatherViewModel @Inject constructor(
    private val getFavouritesUseCase: GetFavouritesUseCase,
    private val addFavouriteCityUseCase: AddFavouriteCityUseCase
) : ViewModel() {

    val favouritesLiveData = MutableLiveData<FavCityWeatherState<Set<String>>>(FavCityWeatherState.NoFavList)

    fun fetch() {
        viewModelScope.launch {
            val response = getFavouritesUseCase.execute()
            favouritesLiveData.value = if (response.isNotEmpty()) {
                FavCityWeatherState.Success(response)
            } else {
                FavCityWeatherState.NoFavList
            }
        }
    }

    fun add(cityName: String) {
        viewModelScope.launch {
            addFavouriteCityUseCase.execute(cityName)
        }
    }
}