package com.weather.usecase

import com.weather.repository.FavouritesRepository
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.Before

import org.junit.Test

class AddFavouriteCityUseCaseTest {

    private lateinit var addFavouriteCityUseCase: AddFavouriteCityUseCase

    @MockK
    private lateinit var favouritesRepository: FavouritesRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        addFavouriteCityUseCase = AddFavouriteCityUseCase(favouritesRepository)
    }

    @Test
    fun `add favourite city to repository`() {
        val city = "Dubai"
        every { favouritesRepository.addFavouriteCity(city) } answers { nothing }
        addFavouriteCityUseCase.execute(city)
        verify { favouritesRepository.addFavouriteCity(city) }
    }
}