package com.test.weatherapp.dataaccess.storage.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.test.weatherapp.vo.City
import com.test.weatherapp.vo.Coordinates
import com.test.weatherapp.vo.WeatherForecast
import java.lang.reflect.Type

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
    fun fromWeatherForecast(mObject: List<WeatherForecast>?): String? {
        return if (mObject != null) Gson().toJson(mObject)
        else null
    }

    @TypeConverter
    fun toWeatherForecast(jsonString: String?): List<WeatherForecast>? {
        val listType: Type = object : TypeToken<List<WeatherForecast>>() {}.type
        return if (!jsonString.isNullOrEmpty()) Gson().fromJson(jsonString, listType)
        else null
    }
}