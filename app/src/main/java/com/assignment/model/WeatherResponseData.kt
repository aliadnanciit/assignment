package com.assignment.model

import com.squareup.moshi.Json

data class WeatherResponseData(
    @Json(name = "main")
    val main: Main,

    @Json(name = "dt")
    val dt: Int = 0,

    @Json(name = "coord")
    val coord: Coord,

    @Json(name = "weather")
    val weather: List<WeatherItem>?,

    @Json(name = "name")
    val name: String = "",

    @Json(name = "cod")
    val cod: Int = 0,

    @Json(name = "id")
    val id: Int = 0,

    @Json(name = "base")
    val base: String = "",

    @Json(name = "wind")
    val wind: Wind
)

data class Main(
    @Json(name = "temp")
    val temp: Double = 0.0,
    @Json(name = "temp_min")
    val tempMin: Double = 0.0,
    @Json(name = "humidity")
    val humidity: Int = 0,
    @Json(name = "pressure")
    val pressure: Int = 0,
    @Json(name = "feels_like")
    val feelsLike: Double = 0.0,
    @Json(name = "temp_max")
    val tempMax: Double = 0.0
)

data class Coord(
    @Json(name = "lon")
    val lon: Double = 0.0,
    @Json(name = "lat")
    val lat: Double = 0.0
)


data class Wind(
    @Json(name = "deg")
    val deg: Int = 0,
    @Json(name = "speed")
    val speed: Double = 0.0
)


data class WeatherItem(
    @Json(name = "icon")
    val icon: String = "",
    @Json(name = "description")
    val description: String = "",
    @Json(name = "main")
    val main: String = "",
    @Json(name = "id")
    val id: Int = 0
)
