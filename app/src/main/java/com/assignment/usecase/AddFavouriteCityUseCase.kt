package com.assignment.usecase

import com.assignment.repository.FavouritesRepository
import javax.inject.Inject

class AddFavouriteCityUseCase @Inject constructor(
    private val favouritesRepository: com.assignment.repository.FavouritesRepository
) {
    suspend fun execute(city: String) {
        favouritesRepository.addFavouriteCity(city)
    }
}
