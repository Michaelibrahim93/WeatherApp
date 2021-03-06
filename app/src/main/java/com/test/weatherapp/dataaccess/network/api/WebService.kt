package com.test.weatherapp.dataaccess.network.api

import com.test.weatherapp.BuildConfig
import com.test.weatherapp.dataaccess.network.response.ForecastResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WebService {
    @GET("data/2.5/onecall?appid=${BuildConfig.OPEN_WEATHER_API_KEY}&exclude=minutely,hourly")
    suspend fun getWeatherForecastList(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double
    ) : ForecastResponse
}