package com.test.weatherapp.ui.formatters

import com.test.weatherapp.R
import com.test.weatherapp.vo.City
import com.test.weatherapp.vo.CityForecast

object CityItemUiHelper {
    @JvmStatic
    fun bookmarkDrawable(cityForecast: CityForecast): Int {
        return if (cityForecast.isBookMarked) R.drawable.ic_bookmarked
        else R.drawable.ic_bookmark_border
    }

    @JvmStatic
    fun cityFullName(city: City?): String {
        if (city == null) return ""
        val stringBuilder = StringBuilder()
        stringBuilder.append(city.name)
        if (!city.state.isNullOrBlank())
            stringBuilder.append(", ${city.state}")
        stringBuilder.append(", ${city.country}")
        return stringBuilder.toString()
    }
}