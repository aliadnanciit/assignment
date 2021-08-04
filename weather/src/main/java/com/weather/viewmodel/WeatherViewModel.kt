package com.weather.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weather.BuildConfig
import com.weather.model.FavCityWeatherState
import com.weather.model.ForecastWeather
import com.weather.model.UserWeatherState
import com.weather.model.WeatherResponseData
import com.weather.usecase.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val searchWeatherByCityNameUseCase: SearchWeatherByCityNameUseCase,
    private val getWeakForecastWeatherUseCase: GetWeakForecastWeatherUseCase,
    private val fetchWeatherByLocationUseCase: FetchWeatherByLocationUseCase,
    private val favouritesUseCase: FavouritesUseCase,
    private val notificationUseCase: NotificationUseCase
) : ViewModel() {

    val weatherForecastLiveData = MutableLiveData<ForecastWeather>()
    val weatherByLocation = MutableLiveData<UserWeatherState<WeatherResponseData>>(UserWeatherState.Loading)

    val favouritesLiveData = MutableLiveData<FavCityWeatherState<Set<String>>>(FavCityWeatherState.NoFavList)

    fun fetchForecastWeather(city: String) {
        viewModelScope.launch {
            val response = getWeakForecastWeatherUseCase.execute(city, BuildConfig.API_KEY, "metric")
            weatherForecastLiveData.value = response
        }
    }

    fun fetchWeatherByLocation(lat: String, lon: String, units: String) {
        weatherByLocation.value = UserWeatherState.Loading

        viewModelScope.launch {
            val response = fetchWeatherByLocationUseCase.execute(
                lat,
                lon,
                BuildConfig.API_KEY,
                "imperial"
            )
            weatherByLocation.value = UserWeatherState.Success(response)
        }
    }

    fun fetchFavourites() {
        viewModelScope.launch {
            val response = favouritesUseCase.getFavouriteCities()
            favouritesLiveData.value = if (response.isNotEmpty()) {
                FavCityWeatherState.Success(response)
            } else {
                FavCityWeatherState.NoFavList
            }
        }
    }

    fun addFav(cityName: String) {
        viewModelScope.launch {
            favouritesUseCase.addFavouriteCity(cityName)
        }
    }

    fun needLocationPermission() {
        weatherByLocation.value = UserWeatherState.PermissionRequired
    }

    fun scheduleNotification(city: String) {
        notificationUseCase.scheduleNotification(city, BuildConfig.API_KEY)
    }
}