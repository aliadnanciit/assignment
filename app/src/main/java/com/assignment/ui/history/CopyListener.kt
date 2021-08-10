package com.assignment.ui.history

import com.assignment.model.ShortUrlModel

interface CopyListener {

    fun onCopy(shortUrlModel: ShortUrlModel)
}