package com.weather.repository

import com.weather.database.WeatherDao
import com.weather.model.WeatherResponseData
import com.weather.service.WeatherApi
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class WeatherRepositoryTest {

    @MockK
    private lateinit var weatherApi: WeatherApi
    @MockK
    private lateinit var weatherResponseData: WeatherResponseData

    @RelaxedMockK
    private lateinit var weatherDao: WeatherDao

    private val testCoroutineDispatcher = TestCoroutineDispatcher()
    private lateinit var repository: WeatherRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repository = WeatherRepositoryImpl(weatherApi, weatherDao, testCoroutineDispatcher)
    }

    @Test
    fun `fetch weather list by city name successfully`() = testCoroutineDispatcher.runBlockingTest {
        val city = "dubai"
        coEvery { weatherApi.getWeatherByCity(city) } returns Response.success(weatherResponseData)

        repository.fetchWeatherListByCityName(city)

        coVerify { weatherApi.getWeatherByCity(city) }
    }
}