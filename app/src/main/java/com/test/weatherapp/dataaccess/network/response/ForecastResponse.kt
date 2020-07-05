package com.test.weatherapp.dataaccess.network.response

import com.test.weatherapp.vo.WeatherForecast

class ForecastResponse(
    val daily: List<WeatherForecast>
) {
}