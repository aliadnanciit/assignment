package com.assignment.common

import javax.inject.Inject

class BuildSdkVersionChecker @Inject constructor(
    private val buildVersion: Int
) {

    fun getSdkVersion() : Int {
        return buildVersion
    }
}