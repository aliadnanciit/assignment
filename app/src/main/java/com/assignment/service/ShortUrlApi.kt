package com.assignment.service

import com.assignment.model.ShortURLResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET

interface ShortUrlApi {

    @GET("shorten")
    suspend fun createShortenUrl(
        @Body url: String
    ): Response<ShortURLResponse>

}