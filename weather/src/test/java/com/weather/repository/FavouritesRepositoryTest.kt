package com.weather.repository

import android.content.SharedPreferences
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test

class FavouritesRepositoryTest {

    @RelaxedMockK
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var favouritesRepository: FavouritesRepository

    @RelaxedMockK
    private lateinit var sharedPreferences: SharedPreferences

    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        favouritesRepository = FavouritesRepository(sharedPreferences, testCoroutineDispatcher)
    }

    @Test
    fun `list favourite cities from preferences`() = testCoroutineDispatcher.runBlockingTest {
        favouritesRepository.getFavouriteCities()
        verify { sharedPreferences.getStringSet("fav_key", emptySet()) }
    }

    @Test
    fun `WHEN favourite cities from preferences are not available THEN return empty set`() = testCoroutineDispatcher.runBlockingTest {
        every { sharedPreferences.getStringSet(any(), any()) } returns null
        assertEquals(emptySet<String>(), favouritesRepository.getFavouriteCities())
    }

    @Test
    fun `WHEN favourite cities from preferences are available THEN return them`() = testCoroutineDispatcher.runBlockingTest {
        val city = "Dubai"
        every { sharedPreferences.getStringSet(any(), any()) } returns setOf(city)
        assertEquals(setOf(city), favouritesRepository.getFavouriteCities())
    }

    @Test
    fun `WHEN adding a favourite city THEN add to preferences`() = testCoroutineDispatcher.runBlockingTest {
        val city = "Dubai"
        val favourites = setOf(city)
        every { sharedPreferences.getStringSet(any(), any()) } returns emptySet()
        every { sharedPreferences.edit() } returns editor
        every { editor.putStringSet(any(), any()) } returns editor
        every { editor.commit() } returns true
        favouritesRepository.addFavouriteCity(city)
        verify { sharedPreferences.edit().putStringSet("fav_key", favourites).apply() }
    }
}