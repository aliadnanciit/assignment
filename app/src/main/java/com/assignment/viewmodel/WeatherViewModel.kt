package com.assignment.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.assignment.model.ForecastWeather
import com.assignment.model.ListItem
import com.assignment.model.UserWeatherState
import com.assignment.model.WeatherResponseData
import com.assignment.usecase.FetchWeatherByLocationUseCase
import com.assignment.usecase.GetWeekForecastWeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val getWeekForecastWeatherUseCase: com.assignment.usecase.GetWeekForecastWeatherUseCase,
    private val fetchWeatherByLocationUseCase: com.assignment.usecase.FetchWeatherByLocationUseCase
) : ViewModel() {

    val weatherForecastLiveData = MutableLiveData<com.assignment.model.ForecastWeather>()
    val weatherByLocation = MutableLiveData<com.assignment.model.UserWeatherState<com.assignment.model.WeatherResponseData>>(
        com.assignment.model.UserWeatherState.Loading)

    fun fetchForecastWeather(city: String) {
        viewModelScope.launch {
            try {
                val response = getWeekForecastWeatherUseCase.execute(city)
                weatherForecastLiveData.value = response
            }
            catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun fetchWeatherByLocation(lat: String, lon: String) {
        if(weatherByLocation.value != null && (weatherByLocation.value is com.assignment.model.UserWeatherState.Success).not()) {
            weatherByLocation.value = com.assignment.model.UserWeatherState.Loading
        }

        viewModelScope.launch {
            val response = fetchWeatherByLocationUseCase.execute(lat, lon)
            weatherByLocation.value = com.assignment.model.UserWeatherState.Success(response)
        }
    }

    fun needLocationPermission() {
        weatherByLocation.value = com.assignment.model.UserWeatherState.PermissionRequired
    }

    fun scheduleNotification(city: String) {

    }

    fun fetchPages() {
        val flow = Pager(PagingConfig(20)) {
            ExamplePagingSource(getWeekForecastWeatherUseCase, "dubai")
        }.flow
            .launchIn(viewModelScope)
    }
}

class ExamplePagingSource(
    private val getWeekForecastWeatherUseCase: com.assignment.usecase.GetWeekForecastWeatherUseCase,
    val city: String
): PagingSource<Int, com.assignment.model.ListItem>() {
    override fun getRefreshKey(state: PagingState<Int, com.assignment.model.ListItem>): Int? {
        return state.anchorPosition?.plus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, com.assignment.model.ListItem> {
        return try {
            val response = getWeekForecastWeatherUseCase.execute(city)
            LoadResult.Page(data = response.list!!, prevKey = null, nextKey = 1)
        } catch (e: java.lang.Exception) {
            LoadResult.Error(e)
        }
    }
}




















