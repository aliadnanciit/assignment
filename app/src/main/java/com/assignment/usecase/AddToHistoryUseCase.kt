package com.assignment.usecase

import com.assignment.repository.ShortenUrlRepository
import javax.inject.Inject

class AddToHistoryUseCase @Inject constructor(
    private val shortenUrlRepository: ShortenUrlRepository
) {

    suspend fun execute(city: String) {
//        return shortenUrlRepository.fetchWeekForecastWeatherList(city, 40)
    }
}