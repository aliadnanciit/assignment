package com.assignment.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.assignment.database.HistoryDao
import com.assignment.database.HistoryEntity
import com.assignment.model.ShortUrlModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named

class HistoryRepositoryImpl @Inject constructor(
    private val historyDao: HistoryDao,
    @Named("IO_DISPATCHER") private val ioDispatcher: CoroutineDispatcher
) : HistoryRepository {

    companion object {
        const val NETWORK_PAGE_SIZE = 30
    }

    override suspend fun delete(id: Int) {
        withContext(ioDispatcher) {
            historyDao.delete(id)
        }
    }

    override suspend fun getHistory(): Flow<PagingData<ShortUrlModel>> {
        return withContext(ioDispatcher) {
            Pager(
                config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = false),
                pagingSourceFactory = {
                    historyDao.getHistory()
                }
            ).flow
                .map { pagingData ->
                    pagingData.map { user ->
                        convert(user)
                    }
                }
        }
    }

    private fun convert(historyEntity: HistoryEntity): ShortUrlModel {
        return ShortUrlModel(
            id = historyEntity.id!!,
            code = historyEntity.code,
            shortLink = historyEntity.shortLink,
            fullShortLink = historyEntity.fullShortLink,
            shortLink2 = historyEntity.shortLink2,
            fullShortLink2 = historyEntity.fullShortLink2,
            shareLink = historyEntity.shareLink,
            fullShareLink = historyEntity.fullShareLink
        )
    }
}
