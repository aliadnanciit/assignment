package com.assignment.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.assignment.databinding.ItemForecastWeatherBinding

class UnlimitedPagingAdapter () : PagingDataAdapter<com.assignment.model.ListItem, WViewHolder>(diff) {

    companion object {
        val diff = object : DiffUtil.ItemCallback<com.assignment.model.ListItem>() {
            override fun areItemsTheSame(oldItem: com.assignment.model.ListItem, newItem: com.assignment.model.ListItem): Boolean {
                return oldItem.dt == newItem.dt
            }

            override fun areContentsTheSame(oldItem: com.assignment.model.ListItem, newItem: com.assignment.model.ListItem): Boolean {
                return oldItem == newItem
            }

        }
    }
    override fun onBindViewHolder(holder: WViewHolder, position: Int) {
        holder.bind(getItem(position)!!)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WViewHolder {
        return WViewHolder(
            ItemForecastWeatherBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }
}

class WViewHolder(
    private val binding: ItemForecastWeatherBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: com.assignment.model.ListItem) {
        binding.root.tag = item
        binding.temperature.text = item.main.temp.toString()
        binding.date.text = item.dtTxt
        binding.humidity.text = item.main.humidity.toString()
        binding.wind.text = item.wind.speed.toString()
    }

}