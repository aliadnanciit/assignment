package com.assignment.usecase

import com.assignment.model.ShortUrlModel
import com.assignment.repository.HistoryRepository
import javax.inject.Inject

class GetHistoryUseCase @Inject constructor(
    private val historyRepository: HistoryRepository
) {

    suspend fun getHistory() : List<ShortUrlModel> {
        return emptyList()
//        return historyRepository.getHistory()
    }
}