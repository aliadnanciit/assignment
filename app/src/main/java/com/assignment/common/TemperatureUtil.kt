package com.assignment.common

import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.roundToInt

@Singleton
class TemperatureUtil @Inject constructor() {

    fun convertToDegree(tempInF: Double) : String {
            val degree = tempInF / 32
            return "${degree.roundToInt()} C"
        }

    fun convertToFarenheit(tempInF: Double) : String {
        return "$tempInF F"
    }
}