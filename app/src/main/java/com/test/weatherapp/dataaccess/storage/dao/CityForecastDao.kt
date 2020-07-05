package com.test.weatherapp.dataaccess.storage.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.test.weatherapp.vo.CityForecast

@Dao
interface CityForecastDao {
    @Query("select * from CityForecast where isBookMarked = 1")
    fun getBookmarkedCities(): LiveData<List<CityForecast>>

    @Query("select * from CityForecast where isBookMarked = 1")
    suspend fun getBookmarkedCitiesSync(): List<CityForecast>

    @Query("select * from CityForecast where id = :id")
    suspend fun getForecastByCityId(id: Long): CityForecast?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCityForecast(cityForecast: CityForecast)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMissingList(list: List<CityForecast>)

    @Query("SELECT COUNT(id) FROM CityForecast where isBookMarked = 1")
    suspend fun getBookmarkedCitiesCount(): Int
}