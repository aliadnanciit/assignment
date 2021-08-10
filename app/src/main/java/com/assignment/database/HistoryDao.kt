package com.assignment.database

import androidx.paging.PagingSource
import androidx.room.*

@Dao
interface HistoryDao {

    @Query("SELECT * FROM HistoryEntity ORDER BY id ASC")
    fun getHistory(): PagingSource<Int, HistoryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(historyEntity: HistoryEntity)

    @Delete
    suspend fun delete(historyEntity: HistoryEntity)

    @Query("DELETE FROM HistoryEntity WHERE id = :id")
    suspend fun delete(id: Int)
}