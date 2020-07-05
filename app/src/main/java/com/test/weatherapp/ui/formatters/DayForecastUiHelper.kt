package com.test.weatherapp.ui.formatters

import com.test.weatherapp.util.TempConverter
import com.test.weatherapp.util.TempConverter.kelvinToCel
import com.test.weatherapp.vo.WeatherForecast
import java.lang.StringBuilder
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

object DayForecastUiHelper {
    @JvmStatic
    fun formatTemp(dTemp: Double, isMin: Boolean): String {
        val stringBuilder = StringBuilder()
        if (isMin)
            stringBuilder.append("/")
        stringBuilder.append(kelvinToCel(dTemp).toString())

        if (isMin)
            stringBuilder.append("°C")
        return stringBuilder.toString()
    }
    @JvmStatic
    fun formatFullTemp(weatherForecast: WeatherForecast?): String {
        if (weatherForecast == null) return ""
        val stringBuilder = StringBuilder()
            .append(kelvinToCel(weatherForecast.temperature.temperatureMax))
            .append("/")
            .append(kelvinToCel(weatherForecast.temperature.temperatureMin))
            .append("°C")
        return stringBuilder.toString()
    }

    @JvmStatic
    fun formatDate(dateFormat: SimpleDateFormat, weatherForecast: WeatherForecast): String {
        return dateFormat.format(Date(weatherForecast.unixTime*1000))
    }
}