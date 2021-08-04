package com.weather.usecase

import com.weather.repository.FavouritesRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runBlockingTest
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
    fun `get favourite cities from repository`() = runBlockingTest {
        coEvery { favouritesRepository.getFavouriteCities() } returns emptySet()
        getFavouritesUseCase.execute()
        coVerify { favouritesRepository.getFavouriteCities() }
    }
}