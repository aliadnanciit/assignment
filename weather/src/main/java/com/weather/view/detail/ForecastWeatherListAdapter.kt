package com.weather.view.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.weather.databinding.ItemForecastWeatherBinding
import com.weather.model.ListItem

class ForecastWeatherListAdapter(): ListAdapter<ListItem, WeatherViewHolder>(weatherDiff) {

    companion object {
        val weatherDiff = object: DiffUtil.ItemCallback<ListItem>() {
            override fun areItemsTheSame(oldItem: ListItem, newItem: ListItem) = oldItem == newItem
            override fun areContentsTheSame(oldItem: ListItem, newItem: ListItem) = oldItem.dt == newItem.dt
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val holder = WeatherViewHolder(
            ItemForecastWeatherBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
//        holder.binding.root.setOnClickListener {
//            val campaign = it.tag as Weather
//        }
        return holder
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class WeatherViewHolder(private val binding: ItemForecastWeatherBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(item: ListItem) {
        binding.root.tag = item
        binding.humidity.text = item.main.humidity.toString()
        binding.temperature.text = item.main.temp.toString()
        binding.wind.text = item.wind.speed.toString()
    }
}