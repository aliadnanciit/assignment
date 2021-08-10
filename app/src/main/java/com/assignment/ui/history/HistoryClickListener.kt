package com.assignment.ui.history

import com.assignment.model.ShortUrlModel

interface HistoryClickListener {

    fun onClick(shortUrlModel: ShortUrlModel)
}