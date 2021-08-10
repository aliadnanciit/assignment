package com.assignment.usecase

import android.os.Build
import com.assignment.common.AppClipboardManager
import com.assignment.common.BuildSdkVersionChecker
import javax.inject.Inject

class CopyToClipboardUseCase @Inject constructor(
    private val buildSdkVersionChecker: BuildSdkVersionChecker,
    private val appClipboardManager: AppClipboardManager
) {
    fun copy(url: String): Boolean {
        return try {
            val sdk = buildSdkVersionChecker.getSdkVersion() //Build.VERSION.SDK_INT
            if (sdk < Build.VERSION_CODES.HONEYCOMB) {
                val clipboard = appClipboardManager.getSystemClipboardManager()
                clipboard.text = url
            } else {
                val clipboard = appClipboardManager.getSystemClipboardManager()
                val clip = appClipboardManager.getClipData(url)
                clipboard.setPrimaryClip(clip)
            }
            true
        } catch (e: Exception) {
            false
        }
    }
}