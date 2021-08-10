package com.assignment.ui.history

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.assignment.R
import com.assignment.databinding.ItemHistoryBinding
import com.assignment.model.ShortUrlModel
import com.assignment.usecase.CopyToClipboardUseCase

class HistoryPagingAdapter(
    private val historyClickListener: HistoryClickListener,
    private val copyToClipboardUseCase: CopyToClipboardUseCase
) : PagingDataAdapter<ShortUrlModel, HistoryViewHolder>(DIFF) {

    companion object {
        val DIFF = object : DiffUtil.ItemCallback<ShortUrlModel>() {
            override fun areItemsTheSame(oldItem: ShortUrlModel, newItem: ShortUrlModel): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ShortUrlModel, newItem: ShortUrlModel): Boolean {
                return oldItem.id == newItem.id
            }

        }
    }
    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(getItem(position)!!)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val binding = ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        binding.imageDelete.setOnClickListener {
            val tag = it.tag as ShortUrlModel
            historyClickListener.onClick(tag)
        }
        binding.buttonCopy.setOnClickListener {
            val btn = it as Button
            val tag = it.tag as ShortUrlModel
            if(tag.isCopied) {
                return@setOnClickListener
            }
            val isCopied = copyToClipboardUseCase.copy(tag.fullShortLink)
            if(isCopied.not()) {
                return@setOnClickListener
            }
            if(tag.isCopied.not()) {
                tag.isCopied = true
                it.setBackgroundColor(it.context.getColor(R.color.violet_color_dark))
                btn.text = "COPIED!"
            }
            else {
                it.setBackgroundColor(it.context.getColor(R.color.cyan_color))
                btn.text = "COPy"
            }
        }
        return HistoryViewHolder(binding)
    }
}

class HistoryViewHolder(
    private val binding: ItemHistoryBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: ShortUrlModel) {
        binding.root.tag = item
        binding.imageDelete.tag = item
        binding.buttonCopy.tag = item

        binding.shortLink.text = item.shortLink
        binding.link.text = item.fullShortLink
    }
}