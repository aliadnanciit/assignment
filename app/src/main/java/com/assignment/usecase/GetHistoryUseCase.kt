package com.assignment.usecase

import androidx.paging.PagingData
import com.assignment.model.ShortUrlModel
import com.assignment.repository.HistoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetHistoryUseCase @Inject constructor(
    private val historyRepository: HistoryRepository
) {

    suspend fun getHistory() : Flow<PagingData<ShortUrlModel>> {
        return historyRepository.getHistory()
    }
}