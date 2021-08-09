package com.assignment.repository

import com.assignment.common.safeApiCall
import com.assignment.database.HistoryDao
import com.assignment.database.HistoryEntity
import com.assignment.model.ShortURL
import com.assignment.service.ShortUrlApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named

class ShortenUrlRepositoryImpl @Inject constructor(
    private val shortUrlApi: ShortUrlApi,
    private val historyDao: HistoryDao,
    @Named("IO_DISPATCHER") private val ioDispatcher: CoroutineDispatcher
) : ShortenUrlRepository {

    override suspend fun createShortenUrl(url: String): ShortURL {
        return withContext(ioDispatcher) {
            val response = safeApiCall {
                shortUrlApi.createShortenUrl(url)
            }
            if (response.isSuccessful) {
                val shortURLResponse = response.body()!!
                saveLink(shortURLResponse.shortURL)
                shortURLResponse.shortURL
            } else {
                throw com.assignment.exception.InvalidURLException("Fail to create shorten url")
            }
        }
    }

    private suspend fun saveLink(shortURL: ShortURL) {
        val historyEntity = shortURL.let {
            HistoryEntity(
                code = it.code,
                shortLink = it.shortLink,
                shortLink2 = it.shortLink2,
                fullShortLink = it.fullShortLink,
                fullShortLink2 = it.fullShortLink2,
                shareLink = it.shareLink,
                fullShareLink = it.fullShortLink
            )
        }
        historyDao.insert(historyEntity)
    }
}