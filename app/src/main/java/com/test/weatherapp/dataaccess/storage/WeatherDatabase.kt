package com.test.weatherapp.dataaccess.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.test.weatherapp.dataaccess.storage.converters.ObjectsConverter
import com.test.weatherapp.vo.City

@Database(
    entities = [City::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(ObjectsConverter::class)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun cityDao(): City
}