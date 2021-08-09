package com.assignment.service

import com.assignment.model.ShortURLResponse
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Query

interface ShortUrlApi {

    @POST("shorten")
    suspend fun createShortenUrl(@Query("url") url: String): Response<ShortURLResponse>
}