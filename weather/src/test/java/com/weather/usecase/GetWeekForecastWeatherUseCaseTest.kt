package com.weather.usecase

import com.weather.model.ForecastWeather
import com.weather.repository.WeatherRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

class GetWeekForecastWeatherUseCaseTest {

    @MockK
    private lateinit var response: ForecastWeather

    private lateinit var getWeekForecastWeatherUseCase: GetWeekForecastWeatherUseCase

    @MockK
    private lateinit var weatherRepository: WeatherRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        getWeekForecastWeatherUseCase = GetWeekForecastWeatherUseCase((weatherRepository))
    }

    @Test
    fun `fetch week forecast weather`() = runBlockingTest {
        val city = "Dubai"

        coEvery { weatherRepository.fetchWeekForecastWeatherList(city, 40) } returns response
        getWeekForecastWeatherUseCase.execute(city)
        coVerify { weatherRepository.fetchWeekForecastWeatherList(city, 40) }
    }
}