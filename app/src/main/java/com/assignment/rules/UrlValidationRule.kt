package com.assignment.rules

import javax.inject.Inject

class UrlValidationRule @Inject constructor() : IsValidRule {

    override fun isValid(url: String): Boolean {
        return true
    }

}
