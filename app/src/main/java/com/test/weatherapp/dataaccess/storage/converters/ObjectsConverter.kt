package com.test.weatherapp.dataaccess.storage.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.test.weatherapp.vo.Coordinates

class ObjectsConverter {
    @TypeConverter
    fun fromCoordinates(coordinates: Coordinates): String {
        return Gson().toJson(coordinates)
    }

    @TypeConverter
    fun toCoordinates(jsonString: String): Coordinates {
        return Gson().fromJson(jsonString, Coordinates::class.java)
    }
}