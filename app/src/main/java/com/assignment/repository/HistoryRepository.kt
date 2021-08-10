package com.assignment.repository

import androidx.paging.PagingData
import com.assignment.model.ShortUrlModel
import kotlinx.coroutines.flow.Flow

interface HistoryRepository {

    suspend fun getHistory() : Flow<PagingData<ShortUrlModel>>

    suspend fun delete(id: Int)
}