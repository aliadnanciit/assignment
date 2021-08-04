package com.weather.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.weather.databinding.ItemFavCityBinding

class FavouriteCitiesWeatherAdapter(
    private val weatherClickListener: CityOnclickListener
) : ListAdapter<String, FavouriteCityViewHolder>(weatherDiff) {

    companion object {
        val weatherDiff = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String) = oldItem == newItem
            override fun areContentsTheSame(oldItem: String, newItem: String) = oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteCityViewHolder {
        val holder = FavouriteCityViewHolder(
            ItemFavCityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
        holder.binding.root.setOnClickListener {
            val city = it.tag as String
            weatherClickListener.onClick(city)
        }
        return holder
    }

    override fun onBindViewHolder(holder: FavouriteCityViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class FavouriteCityViewHolder(val binding: ItemFavCityBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(city: String) {
        binding.root.tag = city
        binding.cityName.text = city
    }
}

interface CityOnclickListener {
    fun onClick(city: String)
}