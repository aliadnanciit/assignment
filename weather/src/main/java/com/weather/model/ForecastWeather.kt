package com.weather.model


import com.squareup.moshi.Json

data class ForecastWeather(
    @Json(name = "city")
    val city: City,
    @Json(name = "cnt")
    val cnt: Int = 0,
    @Json(name = "cod")
    val cod: String = "",
    @Json(name = "message")
    val message: Int = 0,
    @Json(name = "list")
    val list: List<ListItem>?
)


data class City(
    @Json(name = "country")
    val country: String = "",
    @Json(name = "coord")
    val coord: Coord,
    @Json(name = "sunrise")
    val sunrise: Int = 0,
    @Json(name = "timezone")
    val timezone: Int = 0,
    @Json(name = "sunset")
    val sunset: Int = 0,
    @Json(name = "name")
    val name: String = "",
    @Json(name = "id")
    val id: Int = 0,
    @Json(name = "population")
    val population: Int = 0
)


data class ListItem(
    @Json(name = "dt")
    val dt: Int = 0,
    @Json(name = "pop")
    val pop: Float = 0.0f,
    @Json(name = "visibility")
    val visibility: Int = 0,
    @Json(name = "dt_txt")
    val dtTxt: String = "",
    @Json(name = "weather")
    val weather: List<WeatherItem>?,
    @Json(name = "main")
    val main: Main,
    @Json(name = "wind")
    val wind: Wind
)




