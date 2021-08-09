package com.assignment.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.assignment.databinding.ItemForecastWeatherBinding

class UnlimitedPagingAdapter1 : PagingDataAdapter<com.assignment.model.ListItem, MyViewHolder>(
    mydiff
) {

    companion object {
        val mydiff = object : DiffUtil.ItemCallback<com.assignment.model.ListItem>() {
            override fun areItemsTheSame(oldItem: com.assignment.model.ListItem, newItem: com.assignment.model.ListItem): Boolean {
                return oldItem.dt == newItem.dt
            }

            override fun areContentsTheSame(oldItem: com.assignment.model.ListItem, newItem: com.assignment.model.ListItem): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(getItem(position)!!)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ItemForecastWeatherBinding.inflate(
                LayoutInflater.from(parent.context), parent, false)
        )
    }
}

class MyViewHolder(
    val binding: ItemForecastWeatherBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: com.assignment.model.ListItem) {

    }

}

