package com.assignment.usecase

import com.assignment.repository.FavouritesRepository
import javax.inject.Inject

class GetFavouritesUseCase @Inject constructor(
    private val favouritesRepository: com.assignment.repository.FavouritesRepository
) {
    suspend fun execute(): Set<String> {
        return favouritesRepository.getFavouriteCities()
    }
}
