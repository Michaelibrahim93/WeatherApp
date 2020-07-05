package com.test.weatherapp.vo

import com.google.gson.annotations.SerializedName

data class WeatherForecast(
    @SerializedName("dt")
    val unixTime: Long,
    @SerializedName("temp")
    val temperature: Temperature,
    @SerializedName("weather")
    val weatherList: List<WeatherBrief>
) {

    //temperature in fahrenheit
    data class Temperature(
        @SerializedName("min")
        val temperatureMin: Double,
        @SerializedName("max")
        val temperatureMax: Double
    )

    data class WeatherBrief(
        val main: String,
       val description: String
    )
}