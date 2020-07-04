package com.test.weatherapp.dataaccess.repository

import androidx.lifecycle.LiveData
import com.test.weatherapp.dataaccess.network.api.WebService
import com.test.weatherapp.dataaccess.storage.dao.CityForecastDao
import com.test.weatherapp.dataaccess.storage.dao.CityDao
import com.test.weatherapp.vo.CityForecast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CityRepository @Inject constructor(
    private val webService: WebService,
    private val cityDao: CityDao,
    private val cityForecastDao: CityForecastDao
){
    fun loadBookmarkedCities(): LiveData<List<CityForecast>> {
        return cityForecastDao.getBookmarkedCities()
    }

    suspend fun toggleBookmark(cityForecast: CityForecast) = withContext(Dispatchers.IO) {
        val dbCityForecast = cityForecastDao.getCityById(cityForecast.id)
        dbCityForecast.isBookMarked = !cityForecast.isBookMarked
        cityForecastDao.getCityById(cityForecast.id)
    }
}