package com.weather.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.weather.model.ForecastWeather
import com.weather.usecase.FetchWeatherByLocationUseCase
import com.weather.usecase.GetWeekForecastWeatherUseCase
import com.weather.usecase.WeatherNotificationUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class WeatherViewModelTest {

    @get:Rule
    val instantTaskExecutor = InstantTaskExecutorRule()

    private lateinit var viewModel: WeatherViewModel

    @MockK
    private lateinit var getWeekForecastWeatherUseCase: GetWeekForecastWeatherUseCase
    @MockK
    private lateinit var fetchWeatherByLocationUseCase: FetchWeatherByLocationUseCase
    @MockK
    private lateinit var weatherNotificationUseCase: WeatherNotificationUseCase

    @MockK
    private lateinit var responseForecastWeather: ForecastWeather

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = WeatherViewModel(
            getWeekForecastWeatherUseCase, fetchWeatherByLocationUseCase, weatherNotificationUseCase
        )
    }

    @Test
    fun `should show success state`() = runBlockingTest {
        val city = "dubai"
        coEvery { getWeekForecastWeatherUseCase.execute(city) } returns responseForecastWeather
        viewModel.fetchForecastWeather(city)

        Assert.assertEquals(responseForecastWeather, viewModel.weatherForecastLiveData.value)
        coVerify { getWeekForecastWeatherUseCase.execute(city) }
    }
}