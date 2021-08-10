package com.assignment.usecase

import android.content.ClipData
import android.content.Context
import android.os.Build
import com.assignment.exception.InvalidURLException
import com.assignment.model.ShortURL
import com.assignment.repository.ShortenUrlRepository
import com.assignment.rules.UrlValidationRule
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class CopyToClipboardUseCase @Inject constructor(
    @ApplicationContext private val applicationContext: Context
) {
    fun copy(url: String) : Boolean{
        return try {
            val sdk = Build.VERSION.SDK_INT
            if (sdk < Build.VERSION_CODES.HONEYCOMB) {
                val clipboard = applicationContext.getSystemService(Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
                clipboard.text = url
            } else {
                val clipboard = applicationContext.getSystemService(Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
                val clip = ClipData.newPlainText("Label", url)
                clipboard.setPrimaryClip(clip)
            }
            true
        } catch (e: Exception) {
            false
        }
    }
}