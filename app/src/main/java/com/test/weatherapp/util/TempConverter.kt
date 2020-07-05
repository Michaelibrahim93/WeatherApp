package com.test.weatherapp.util

import kotlin.math.roundToInt

object TempConverter {
    fun kelvinToCel(kTemp: Double): Int {
        return (kTemp-273.15).roundToInt()
    }
}