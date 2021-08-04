package com.weather.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.weather.common.TemperatureUtil
import com.weather.databinding.ItemForecastWeatherBinding
import com.weather.model.ListItem

class ForecastWeatherListAdapter(
    private val temperatureUtil: TemperatureUtil,
    private val showTempInDegree: Boolean
) : ListAdapter<ListItem, WeatherViewHolder>(weatherDiff) {

    companion object {
        val weatherDiff = object : DiffUtil.ItemCallback<ListItem>() {
            override fun areItemsTheSame(oldItem: ListItem, newItem: ListItem) = oldItem == newItem
            override fun areContentsTheSame(oldItem: ListItem, newItem: ListItem) =
                oldItem.dt == newItem.dt
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        return WeatherViewHolder(
            ItemForecastWeatherBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            temperatureUtil,
            showTempInDegree
        )
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class WeatherViewHolder(
    private val binding: ItemForecastWeatherBinding,
    private val temperatureUtil: TemperatureUtil,
    private val showTempInDegree: Boolean
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: ListItem) {
        binding.root.tag = item
        binding.temperature.text = if (showTempInDegree) {
            temperatureUtil.convertToDegree(item.main.temp)
        } else {
            temperatureUtil.convertToFarenheit(item.main.temp)
        }
        binding.date.text = item.dtTxt
        binding.humidity.text = item.main.humidity.toString()
        binding.wind.text = item.wind.speed.toString()
    }
}