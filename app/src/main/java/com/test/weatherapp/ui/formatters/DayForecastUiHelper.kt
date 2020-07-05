package com.test.weatherapp.ui.formatters

import com.test.weatherapp.vo.WeatherForecast
import java.lang.StringBuilder
import java.text.SimpleDateFormat
import java.util.*

object DayForecastUiHelper {
    @JvmStatic
    fun formatTemp(dTemp: Double, isMin: Boolean): String {
        val stringBuilder = StringBuilder()
        if (isMin)
            stringBuilder.append("/")
        stringBuilder.append((dTemp-273.15).toInt().toString())

        if (isMin)
            stringBuilder.append("°C")
        return stringBuilder.toString()
    }

    @JvmStatic
    fun formatDate(dateFormat: SimpleDateFormat, weatherForecast: WeatherForecast): String {
        return dateFormat.format(Date(weatherForecast.unixTime*1000))
    }
}