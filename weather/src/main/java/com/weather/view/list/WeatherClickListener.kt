package com.weather.view.list

import com.weather.model.Weather

interface WeatherClickListener {
    fun onclick(weather: Weather)
}