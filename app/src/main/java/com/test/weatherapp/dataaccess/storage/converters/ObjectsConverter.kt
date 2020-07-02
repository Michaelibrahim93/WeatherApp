package com.test.weatherapp.dataaccess.storage.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.test.weatherapp.vo.Coordinates
import com.test.weatherapp.vo.WeatherForecast

class ObjectsConverter {
    @TypeConverter
    fun fromCoordinates(coordinates: Coordinates): String {
        return Gson().toJson(coordinates)
    }

    @TypeConverter
    fun toCoordinates(jsonString: String): Coordinates {
        return Gson().fromJson(jsonString, Coordinates::class.java)
    }

    @TypeConverter
    fun fromWeatherForecast(mObject: WeatherForecast?): String? {
        return if (mObject != null) Gson().toJson(mObject)
        else null
    }

    @TypeConverter
    fun toWeatherForecast(jsonString: String?): WeatherForecast? {
        return if (!jsonString.isNullOrEmpty()) Gson().fromJson(jsonString, WeatherForecast::class.java)
        else null
    }
}