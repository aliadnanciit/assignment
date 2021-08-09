package com.assignment.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named

class FavouritesRepository @Inject constructor(
    @Named("IO_DISPATCHER") private val ioDispatcher: CoroutineDispatcher
) {

    suspend fun addFavouriteCity(city: String) {
        withContext(ioDispatcher) {
//            val favourites = getFavouriteCities().toMutableSet()
        }
    }
}
