package com.test.weatherapp.vo

import com.google.gson.annotations.SerializedName

data class WeatherForecast(
    @SerializedName("dt")
    val unixTime: Long,
    val weatherMain: WeatherMain
) {

    //temperature in fahrenheit
    data class WeatherMain(
        val temperatureMin: Double,
        val temperatureMax: Double
    )
}