package com.assignment.repository

import androidx.paging.*
import com.assignment.database.HistoryDao
import com.assignment.database.HistoryEntity
import com.assignment.model.ShortUrlModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
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

    override suspend fun add() {
        withContext(ioDispatcher) {

        }
    }

    override fun getHistory(): Flow<PagingData<ShortUrlModel>> {
        return Pager(
                config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = false),
                pagingSourceFactory = {
                    HistoryPagingSource(historyDao)
                }
            ).flow
                .map { pagingData ->
                    pagingData.map { user ->
                        convert(user)
                    }
                }
    }

    private fun convert(historyEntity: HistoryEntity): ShortUrlModel {
        return ShortUrlModel(
            id = historyEntity.uid!!,
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
