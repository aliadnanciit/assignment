package com.assignment.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.assignment.database.HistoryDao
import com.assignment.database.HistoryEntity

class HistoryPagingSource(
    private val historyDao: HistoryDao
): PagingSource<Int, HistoryEntity>() {

    private companion object {
        const val INITIAL_PAGE_INDEX = 0
    }

    override fun getRefreshKey(state: PagingState<Int, HistoryEntity>): Int? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, HistoryEntity> {
        val position = params.key ?: INITIAL_PAGE_INDEX
        val randomPosts = historyDao.getHistory()
        return LoadResult.Page(
            data = randomPosts,
            prevKey = if (position == INITIAL_PAGE_INDEX) null else position - 1,
            nextKey = if (randomPosts.isNullOrEmpty()) null else position + 1
        )
    }
}