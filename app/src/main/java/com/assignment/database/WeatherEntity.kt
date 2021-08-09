package com.assignment.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WeatherEntity(
    @PrimaryKey(autoGenerate = true) val uid: Int,
    val name: String,
    val temperature: Double,
    val humidity: Int = 0,
    val speed: Double = 0.0
)
