package com.assignment.repository

import com.assignment.database.HistoryDao
import com.assignment.model.ShortUrlModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named

class HistoryRepositoryImpl @Inject constructor(
    private val historyDao: HistoryDao,
    @Named("IO_DISPATCHER") private val ioDispatcher: CoroutineDispatcher
) : HistoryRepository {

    override suspend fun add() {
        withContext(ioDispatcher) {
//            val favourites = getFavouriteCities().toMutableSet()
        }
    }

    override suspend fun getHistory() : List<ShortUrlModel> {
        return withContext(ioDispatcher) {
            historyDao.getHistory()
                .map {
                    ShortUrlModel(
                        code = "",
                        shortLink = "",
                        fullShortLink = "",
                        shortLink2 = "",
                        fullShortLink2 = "",
                        shareLink = "",
                        fullShareLink = ""
                    )
                }
        }
    }
}
