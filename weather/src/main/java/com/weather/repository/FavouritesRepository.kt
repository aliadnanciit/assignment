package com.weather.repository

import android.content.SharedPreferences
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named

private const val PREF_FAV_KEY = "fav_key"

class FavouritesRepository @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    @Named("IO_DISPATCHER") private val ioDispatcher: CoroutineDispatcher
) {

    suspend fun getFavouriteCities(): Set<String> {
        return withContext(ioDispatcher) {
            sharedPreferences.getStringSet(PREF_FAV_KEY, emptySet()) ?: emptySet()
        }
    }

    suspend fun addFavouriteCity(city: String) {
        withContext(ioDispatcher) {
            val favourites = getFavouriteCities().toMutableSet()
            if (favourites.add(city)) {
                sharedPreferences.edit().putStringSet(PREF_FAV_KEY, favourites).apply()
            }
        }
    }
}
