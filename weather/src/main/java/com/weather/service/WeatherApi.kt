package com.weather.service

import com.weather.model.server.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET

interface WeatherApi {

    @GET("cms/test/campaigns.json")
    suspend fun getCampaigns(): Response<WeatherResponse>
}