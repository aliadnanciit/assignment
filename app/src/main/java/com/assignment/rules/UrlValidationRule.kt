package com.assignment.rules

import android.webkit.URLUtil
import javax.inject.Inject

class UrlValidationRule @Inject constructor() : IsValidRule {

    override fun isValid(url: String): Boolean {
        return URLUtil.isValidUrl(url)
    }

}
