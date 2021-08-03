package com.weather.view.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.weather.databinding.ItemWeatherBinding
import com.weather.model.Weather

class WeatherListAdapter(
    private val weatherClickListener: WeatherClickListener
): ListAdapter<Weather, WeatherViewHolder>(weatherDiff) {

    companion object {
        val weatherDiff = object: DiffUtil.ItemCallback<Weather>() {
            override fun areItemsTheSame(oldItem: Weather, newItem: Weather) = oldItem == newItem
            override fun areContentsTheSame(oldItem: Weather, newItem: Weather) = oldItem.name == newItem.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val holder = WeatherViewHolder(
            ItemWeatherBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
        holder.binding.root.setOnClickListener {
            val campaign = it.tag as Weather
            weatherClickListener.onclick(campaign)
        }
        return holder
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class WeatherViewHolder(internal val binding: ItemWeatherBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(weather: Weather) {
        binding.root.tag = weather
        binding.campaignName.text = weather.name
        binding.campaignDescription.text = weather.description
        Glide.with(binding.campaignImage.context)
            .load(weather.imageUrl)
            .into(binding.campaignImage)
    }
}