package com.assignment.ui.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.assignment.databinding.ItemHistoryBinding

class HistoryPagingAdapter() : PagingDataAdapter<com.assignment.model.ListItem, HistoryViewHolder>(diff) {

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
    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(getItem(position)!!)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        return HistoryViewHolder(
            ItemHistoryBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }
}

class HistoryViewHolder(
    private val binding: ItemHistoryBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: com.assignment.model.ListItem) {
        binding.root.tag = item
    }

}