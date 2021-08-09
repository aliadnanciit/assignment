package com.assignment.repository

import androidx.paging.PagingData
import com.assignment.model.ShortUrlModel
import kotlinx.coroutines.flow.Flow

interface HistoryRepository {

    suspend fun add()

    fun getHistory() : Flow<PagingData<ShortUrlModel>>
}