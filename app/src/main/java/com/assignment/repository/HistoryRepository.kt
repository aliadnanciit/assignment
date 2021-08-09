package com.assignment.repository

import com.assignment.model.ShortUrlModel

interface HistoryRepository {

    suspend fun add()

    suspend fun getHistory() : List<ShortUrlModel>
}