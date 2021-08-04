package com.weather.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.weather.model.FavCityWeatherState
import com.weather.usecase.AddFavouriteCityUseCase
import com.weather.usecase.GetFavouritesUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class FavCityWeatherViewModelTest {

    @get:Rule
    val instantTaskExecutor = InstantTaskExecutorRule()

    @MockK
    private lateinit var getFavouritesUseCase: GetFavouritesUseCase
    @MockK
    private lateinit var addFavouriteCityUseCase: AddFavouriteCityUseCase

    private lateinit var favCityWeatherViewModel : FavCityWeatherViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        favCityWeatherViewModel = FavCityWeatherViewModel(getFavouritesUseCase, addFavouriteCityUseCase)
    }

    @Test
    fun `fetch favourite city list`() = runBlockingTest {
        val favouriteCities = setOf("dubai")
        coEvery { getFavouritesUseCase.execute() } returns favouriteCities

        favCityWeatherViewModel.fetch()

        assertEquals(FavCityWeatherState.Success(favouriteCities), favCityWeatherViewModel.favouritesLiveData.value)
        coVerify { getFavouritesUseCase.execute() }
    }

    @Test
    fun `verify no favourite city list`() = runBlockingTest {
        coEvery { getFavouritesUseCase.execute() } returns emptySet()

        favCityWeatherViewModel.fetch()

        assertEquals(FavCityWeatherState.NoFavList, favCityWeatherViewModel.favouritesLiveData.value)
        coVerify { getFavouritesUseCase.execute() }
    }
}