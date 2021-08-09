package com.assignment.repository

import com.assignment.model.ShortURL

interface ShortenUrlRepository {

    suspend fun createShortenUrl(url: String): ShortURL

}