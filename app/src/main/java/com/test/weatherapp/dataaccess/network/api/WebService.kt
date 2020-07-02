package com.test.weatherapp.dataaccess.network.api

import com.test.weatherapp.BuildConfig
import com.test.weatherapp.dataaccess.network.response.ListResponse
import com.test.weatherapp.vo.WeatherForecast
import retrofit2.http.GET
import retrofit2.http.Query

interface WebService {
    @GET("data/2.5/forecast?appid=${BuildConfig.OPEN_WEATHER_API_KEY}")
    suspend fun getWeatherForecastList(@Query("id") cityId: Long) : ListResponse<WeatherForecast>
}