package com.assignment.usecase

import com.assignment.repository.ShortenUrlRepository
import javax.inject.Inject

class CreateShortURLUseCase @Inject constructor(
    private val shortenUrlRepository: ShortenUrlRepository
) {
    suspend fun create(city: String) {
//        return shortenUrlRepository.fetchWeatherListByCityName(city)
    }
}