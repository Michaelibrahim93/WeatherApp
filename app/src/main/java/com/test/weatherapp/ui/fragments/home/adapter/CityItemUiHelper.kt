package com.test.weatherapp.ui.fragments.home.adapter

import com.test.weatherapp.R
import com.test.weatherapp.vo.CityForecast

object CityItemUiHelper {
    @JvmStatic
    fun bookmarkDrawable(cityForecast: CityForecast): Int {
        return if (cityForecast.isBookMarked) R.drawable.ic_bookmarked
        else R.drawable.ic_bookmark_border
    }

    @JvmStatic
    fun cityFullName(cityForecast: CityForecast): String {
        val stringBuilder = StringBuilder()
        stringBuilder.append(cityForecast.name)
        if (!cityForecast.state.isNullOrBlank())
            stringBuilder.append(", ${cityForecast.state}")
        stringBuilder.append(", ${cityForecast.country}")
        return stringBuilder.toString()
    }
}