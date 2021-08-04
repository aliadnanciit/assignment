package com.weather.usecase

import com.weather.repository.FavouritesRepository
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before

import org.junit.Test

class AddFavouriteCityUseCaseTest {

    private lateinit var addFavouriteCityUseCase: AddFavouriteCityUseCase

    @MockK
    private lateinit var favouritesRepository: FavouritesRepository

    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        addFavouriteCityUseCase = AddFavouriteCityUseCase(favouritesRepository)
    }

    @Test
    fun `add favourite city to repository`() = runBlockingTest {
        val city = "Dubai"
        coEvery { favouritesRepository.addFavouriteCity(city) } answers { nothing }
        addFavouriteCityUseCase.execute(city)
        coVerify { favouritesRepository.addFavouriteCity(city) }
    }
}