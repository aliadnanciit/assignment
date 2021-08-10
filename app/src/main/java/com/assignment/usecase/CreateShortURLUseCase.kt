package com.assignment.usecase

import com.assignment.exception.InvalidURLException
import com.assignment.model.ShortURL
import com.assignment.repository.ShortenUrlRepository
import com.assignment.rules.UrlValidationRule
import javax.inject.Inject

class CreateShortURLUseCase @Inject constructor(
    private val urlValidationRule: UrlValidationRule,
    private val shortenUrlRepository: ShortenUrlRepository
) {
    suspend fun create(url: String) : ShortURL {
        val updatedUrl = url.replace(" ", "%20")
        if(urlValidationRule.isValid(updatedUrl).not()) {
            throw InvalidURLException("Invalid url")
        }
        return shortenUrlRepository.createShortenUrl(updatedUrl)
    }
}