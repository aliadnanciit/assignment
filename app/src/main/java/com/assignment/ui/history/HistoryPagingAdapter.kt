package com.assignment.ui.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.assignment.databinding.ItemHistoryBinding
import com.assignment.model.ShortUrlModel

class HistoryPagingAdapter : PagingDataAdapter<ShortUrlModel, HistoryViewHolder>(diff) {

    companion object {
        val diff = object : DiffUtil.ItemCallback<ShortUrlModel>() {
            override fun areItemsTheSame(oldItem: ShortUrlModel, newItem: ShortUrlModel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ShortUrlModel, newItem: ShortUrlModel): Boolean {
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

    fun bind(item: ShortUrlModel) {
        binding.root.tag = item

        binding.shortLink.text = item.shortLink
        binding.link.text = item.fullShortLink
    }
}