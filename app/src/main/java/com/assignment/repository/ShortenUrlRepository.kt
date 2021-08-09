package com.assignment.repository

import com.assignment.model.ShortURLResponse

interface ShortenUrlRepository {

    suspend fun createShortenUrl(url: String): ShortURLResponse

}