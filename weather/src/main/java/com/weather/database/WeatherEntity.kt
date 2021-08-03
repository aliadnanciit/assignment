package com.weather.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WeatherEntity(
    @PrimaryKey(autoGenerate = true) val uid: Int,
    val name: String,
    val description: String,
    val imageUrl: String
)
