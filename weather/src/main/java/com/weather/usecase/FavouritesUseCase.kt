package com.weather.usecase

import com.weather.repository.FavouritesRepository
import javax.inject.Inject

class FavouritesUseCase @Inject constructor(
    private val favouritesRepository: FavouritesRepository
) {
    fun getFavouriteCities(): Set<String> {
        return favouritesRepository.getFavouriteCities()
    }

    fun addFavouriteCity(city: String) {
        favouritesRepository.addFavouriteCity(city)
    }
}
