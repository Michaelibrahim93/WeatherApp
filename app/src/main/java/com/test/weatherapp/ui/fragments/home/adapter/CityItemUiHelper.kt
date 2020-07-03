package com.test.weatherapp.ui.fragments.home.adapter

import com.test.weatherapp.R
import com.test.weatherapp.vo.CityForecast

object CityItemUiHelper {
    @JvmStatic
    fun bookmarkDrawable(cityForecast: CityForecast): Int {
        return if (cityForecast.isBookMarked) R.drawable.ic_bookmarked
        else R.drawable.ic_bookmark_border
    }
}