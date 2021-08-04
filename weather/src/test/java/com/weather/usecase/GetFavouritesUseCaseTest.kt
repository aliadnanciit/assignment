package com.weather.usecase

import com.weather.repository.FavouritesRepository
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.Before

import org.junit.Test

class GetFavouritesUseCaseTest {

    private lateinit var getFavouritesUseCase: GetFavouritesUseCase

    @MockK
    private lateinit var favouritesRepository: FavouritesRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        getFavouritesUseCase = GetFavouritesUseCase(favouritesRepository)
    }

    @Test
    fun `get favourite cities from repository`() {
        every { favouritesRepository.getFavouriteCities() } returns emptySet()
        getFavouritesUseCase.execute()
        verify { favouritesRepository.getFavouriteCities() }
    }
}