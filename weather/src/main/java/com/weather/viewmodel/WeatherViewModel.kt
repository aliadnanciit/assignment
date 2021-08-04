package com.weather.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weather.BuildConfig
import com.weather.model.FavCityWeatherState
import com.weather.model.ForecastWeather
import com.weather.model.UserWeatherState
import com.weather.model.WeatherResponseData
import com.weather.model.server.WeatherStates
import com.weather.usecase.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val getWeatherListUseCase: GetWeatherListUseCase,
    private val searchWeatherByCityNameUseCase: SearchWeatherByCityNameUseCase,
    private val getWeakForecastWeatherUseCase: GetWeakForecastWeatherUseCase,
    private val fetchWeatherByLocationUseCase: FetchWeatherByLocationUseCase,
    private val favouritesUseCase: FavouritesUseCase,
    private val notificationUseCase: NotificationUseCase
) : ViewModel() {

    private val _weatherStateFlow = MutableStateFlow<WeatherStates>(WeatherStates.Loading)
    val weatherStateFlow: StateFlow<WeatherStates> = _weatherStateFlow

    val weatherForecastLiveData = MutableLiveData<ForecastWeather>()
    val weatherByLocation = MutableLiveData<UserWeatherState<WeatherResponseData>>(UserWeatherState.Loading)

    val favouritesLiveData = MutableLiveData<FavCityWeatherState<Set<String>>>(FavCityWeatherState.NoFavList)


    init {
        getWeather()
    }

    private fun getWeather() {
        _weatherStateFlow.value = WeatherStates.Loading
        viewModelScope.launch {
            getWeatherListUseCase.execute()
                .catch {
                    _weatherStateFlow.value = WeatherStates.Error(it)
                }
                .collect {
                    if (it.isEmpty()) {
                        _weatherStateFlow.value = WeatherStates.NoContent
                    } else {
                        _weatherStateFlow.value = WeatherStates.Success(it)
                    }
                }
        }
    }

    fun fetchCampaigns() {
        viewModelScope.launch {
            searchWeatherByCityNameUseCase.execute("dubai", BuildConfig.API_KEY)
        }
    }

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
                "metric"
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