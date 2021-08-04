package com.weather.usecase

import com.weather.model.WeatherResponseData
import com.weather.repository.WeatherRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

class FetchWeatherByLocationUseCaseTest {

    @MockK
    private lateinit var response: WeatherResponseData

    private lateinit var fetchWeatherByLocationUseCase: FetchWeatherByLocationUseCase

    @MockK
    private lateinit var weatherRepository: WeatherRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        fetchWeatherByLocationUseCase = FetchWeatherByLocationUseCase(weatherRepository)
    }

    @Test
    fun `fetch weather by location`() = runBlockingTest {
        val lat = "12.34"
        val lon = "45.67"

        coEvery { weatherRepository.fetchWeatherByLocation(lat, lon) } returns response
        fetchWeatherByLocationUseCase.execute(lat, lon)
        coVerify { weatherRepository.fetchWeatherByLocation(lat, lon) }
    }
}