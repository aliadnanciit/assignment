package com.assignment.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged

@Dao
abstract class WeatherDao {

    @Query("SELECT * FROM WeatherEntity")
    abstract fun getAll(): Flow<List<WeatherEntity>>

    fun getAllDistinctUntilChanged() = getAll().distinctUntilChanged()

    @Query("SELECT * FROM WeatherEntity WHERE uid = (:uid)")
    abstract fun loadById(uid: Int): WeatherEntity

    @Insert
    abstract fun insertAll(list: List<WeatherEntity>)

    @Delete
    abstract fun delete(weatherEntity: WeatherEntity)

    @Query("DELETE FROM WeatherEntity")
    abstract fun deleteAll()
}