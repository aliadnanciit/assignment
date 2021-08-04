package com.weather.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weather.model.ForecastWeather
import com.weather.model.UserWeatherState
import com.weather.model.WeatherResponseData
import com.weather.usecase.FetchWeatherByLocationUseCase
import com.weather.usecase.GetWeakForecastWeatherUseCase
import com.weather.usecase.WeatherNotificationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val getWeakForecastWeatherUseCase: GetWeakForecastWeatherUseCase,
    private val fetchWeatherByLocationUseCase: FetchWeatherByLocationUseCase,
    private val weatherNotificationUseCase: WeatherNotificationUseCase
) : ViewModel() {

    val weatherForecastLiveData = MutableLiveData<ForecastWeather>()
    val weatherByLocation = MutableLiveData<UserWeatherState<WeatherResponseData>>(UserWeatherState.Loading)

    fun fetchForecastWeather(city: String) {
        viewModelScope.launch {
            val response = getWeakForecastWeatherUseCase.execute(city)
            weatherForecastLiveData.value = response
        }
    }

    fun fetchWeatherByLocation(lat: String, lon: String) {
        if(weatherByLocation.value != null && (weatherByLocation.value is UserWeatherState.Success).not()) {
            weatherByLocation.value = UserWeatherState.Loading
        }

        viewModelScope.launch {
            val response = fetchWeatherByLocationUseCase.execute(lat, lon)
            weatherByLocation.value = UserWeatherState.Success(response)
        }
    }

    fun needLocationPermission() {
        weatherByLocation.value = UserWeatherState.PermissionRequired
    }

    fun scheduleNotification(city: String) {
        weatherNotificationUseCase.scheduleNotification(city)
    }
}