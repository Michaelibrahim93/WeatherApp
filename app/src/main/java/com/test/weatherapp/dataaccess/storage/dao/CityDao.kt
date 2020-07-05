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

    @Query("SELECT * FROM City WHERE name LIKE '%' || :cityName|| '%' LIMIT :limit OFFSET :offset")
    suspend fun searchExact(cityName: String, limit: Int, offset: Int): List<City>

    @Query("SELECT * FROM City WHERE id = :cityId")
    suspend fun getCityById(cityId: Long): City

    @Query("SELECT * FROM City WHERE name = :cityName AND country = :country")
    suspend fun searchExact(cityName: String, country: String): City?

    @Query("SELECT * FROM City WHERE country = :country")
    suspend fun searchCountry(country: String): List<City>
}