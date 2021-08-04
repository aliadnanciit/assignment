package com.weather.usecase

import com.weather.repository.FavouritesRepository
import javax.inject.Inject

class GetFavouritesUseCase @Inject constructor(
    private val favouritesRepository: FavouritesRepository
) {
    suspend fun execute(): Set<String> {
        return favouritesRepository.getFavouriteCities()
    }
}
