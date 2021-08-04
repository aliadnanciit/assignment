package com.weather.usecase

import com.weather.repository.FavouritesRepository
import javax.inject.Inject

class AddFavouriteCityUseCase @Inject constructor(
    private val favouritesRepository: FavouritesRepository
) {
    fun execute(city: String) {
        favouritesRepository.addFavouriteCity(city)
    }
}
