package com.test.weatherapp.dataaccess.storage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.test.weatherapp.vo.City

@Dao
interface CityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCities(cities: List<City>)

    @Query("SELECT COUNT(id) FROM City")
    suspend fun countItems(): Int
}