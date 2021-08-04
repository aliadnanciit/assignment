package com.weather.usecase

import com.weather.repository.FavouritesRepository
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.Before

import org.junit.Test

class FavouritesUseCaseTest {

    private lateinit var favouritesUseCase: FavouritesUseCase

    @MockK
    private lateinit var favouritesRepository: FavouritesRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        favouritesUseCase = FavouritesUseCase(favouritesRepository)
    }

    @Test
    fun `add favourite city to repository`() {
        val city = "Dubai"
        every { favouritesRepository.addFavouriteCity(city) } answers { nothing }
        favouritesUseCase.addFavouriteCity(city)
        verify { favouritesRepository.addFavouriteCity(city) }
    }

    @Test
    fun `get favourite cities from repository`() {
        every { favouritesRepository.getFavouriteCities() } returns emptySet()
        favouritesUseCase.getFavouriteCities()
        verify { favouritesRepository.getFavouriteCities() }
    }
}