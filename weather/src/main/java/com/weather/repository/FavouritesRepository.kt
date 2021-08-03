package com.weather.repository

import android.content.SharedPreferences
import javax.inject.Inject

private const val PREF_FAV_KEY = "fav_key"

class FavouritesRepository @Inject constructor(
    private val sharedPreferences: SharedPreferences,
) {

    fun getFavouriteCities(): Set<String> {
        return sharedPreferences.getStringSet(PREF_FAV_KEY, emptySet()) ?: emptySet()
    }

    fun addFavouriteCity(city: String) {
        val favourites = getFavouriteCities().toMutableSet()
        if (favourites.add(city)) {
            sharedPreferences.edit().putStringSet(PREF_FAV_KEY, favourites).apply()
        }
    }
}
