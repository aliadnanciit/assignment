package com.weather.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weather.model.ForecastWeather
import com.weather.model.WeatherResponseData
import com.weather.model.server.WeatherStates
import com.weather.usecase.FetchWeatherByLocationUseCase
import com.weather.usecase.GetWeakForecastWeatherUseCase
import com.weather.usecase.GetWeatherListUseCase
import com.weather.usecase.SearchWeatherByCityNameUseCase
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
    private val fetchWeatherByLocationUseCase: FetchWeatherByLocationUseCase
) : ViewModel() {

    private val _weatherStateFlow = MutableStateFlow<WeatherStates>(WeatherStates.Loading)
    val weatherStateFlow: StateFlow<WeatherStates> = _weatherStateFlow

    val weatherForecastLiveData = MutableLiveData<ForecastWeather>()
    val weatherByLocation = MutableLiveData<WeatherResponseData>()

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
                    if(it.isEmpty()) {
                        _weatherStateFlow.value = WeatherStates.NoContent
                    }
                    else {
                        _weatherStateFlow.value = WeatherStates.Success(it)
                    }
                }
        }
    }

    fun fetchCampaigns() {
        viewModelScope.launch {
            searchWeatherByCityNameUseCase.execute("dubai", "710c6ff29ebad2f6059e31dd6c25923a")
        }
    }

    fun fetchForecastWeather(city: String) {
        viewModelScope.launch {
            val response = getWeakForecastWeatherUseCase.execute(city, "710c6ff29ebad2f6059e31dd6c25923a", "metric")
            weatherForecastLiveData.value = response
        }
    }

    fun fetchWeatherByLocation(lat: String, lon: String, units: String) {
        viewModelScope.launch {
            val response = fetchWeatherByLocationUseCase.execute(
                lat,
                lon,
                "710c6ff29ebad2f6059e31dd6c25923a",
                "metric"
            )
            weatherByLocation.value = response
        }
    }
}