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

class SearchWeatherByCityNameUseCaseTest {

    @MockK
    private lateinit var response: WeatherResponseData

    @MockK
    private lateinit var weatherRepository: WeatherRepository

    private lateinit var searchWeatherByCityNameUseCase: SearchWeatherByCityNameUseCase
    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        searchWeatherByCityNameUseCase = SearchWeatherByCityNameUseCase(weatherRepository)
    }

    @Test
    fun `search weather by city name`() = runBlockingTest {
        val city = "dubai"

        coEvery { weatherRepository.fetchWeatherListByCityName(city) } returns response
        searchWeatherByCityNameUseCase.execute(city)
        coVerify { weatherRepository.fetchWeatherListByCityName(city) }
    }
}