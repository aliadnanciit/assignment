package com.assignment.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.assignment.usecase.CreateShortURLUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val createShortURLUseCase: CreateShortURLUseCase
) : ViewModel() {

    val weatherForecastLiveData = MutableLiveData<com.assignment.model.ForecastWeather>()
    val weatherByLocation = MutableLiveData<com.assignment.model.UserWeatherState<com.assignment.model.WeatherResponseData>>(
        com.assignment.model.UserWeatherState.Loading)

    fun fetchForecastWeather(city: String) {
        viewModelScope.launch {
            try {
//                val response = createShortURLUseCase.create(city)
//                weatherForecastLiveData.value = response
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
//            val response = fetchWeatherByLocationUseCase.execute(lat, lon)
//            weatherByLocation.value = com.assignment.model.UserWeatherState.Success(response)
        }
    }

    fun fetchPages() {
//        val flow = Pager(PagingConfig(20)) {
//            ExamplePagingSource(createShortURLUseCase, "dubai")
//        }.flow
//            .launchIn(viewModelScope)
    }
}

class ExamplePagingSource(
    private val getHistoryUseCase: com.assignment.usecase.GetHistoryUseCase,
    val city: String
): PagingSource<Int, com.assignment.model.ListItem>() {
    override fun getRefreshKey(state: PagingState<Int, com.assignment.model.ListItem>): Int? {
        return state.anchorPosition?.plus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, com.assignment.model.ListItem> {
        return try {
            val response = getHistoryUseCase.execute(city)
            LoadResult.Page(data = response.list!!, prevKey = null, nextKey = 1)
        } catch (e: java.lang.Exception) {
            LoadResult.Error(e)
        }
    }
}