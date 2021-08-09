package com.assignment.repository

import com.assignment.common.safeApiCall
import com.assignment.database.HistoryDao
import com.assignment.model.ShortURLResponse
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

    override suspend fun createShortenUrl(url: String): ShortURLResponse {
        return withContext(ioDispatcher) {
            val response = safeApiCall {
                shortUrlApi.createShortenUrl(url)
            }
            if (response.isSuccessful) {
                val shortURLResponse = response.body()!!
                saveLink(shortURLResponse)
                shortURLResponse
            } else {
                throw com.assignment.exception.InvalidURLException("Fail to create shorten url")
            }
        }
    }

    private fun saveLink(shortURLResponse: ShortURLResponse) {
//        val historyEntity = HistoryEntity(
//            code = shortURLResponse.result.code
//        )
//        historyDao.insert(historyEntity)
    }
}