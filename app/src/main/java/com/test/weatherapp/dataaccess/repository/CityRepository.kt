package com.test.weatherapp.dataaccess.repository

import com.test.weatherapp.dataaccess.storage.dao.CityDao
import com.test.weatherapp.vo.City
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CityRepository @Inject constructor(
    private val cityDao: CityDao
) {
    suspend fun search(query: String, loadSize: Int, offset: Int): List<City> = withContext(
        Dispatchers.IO) {
        cityDao.searchExact(query, loadSize, offset)
    }

    suspend fun searchExact(cityQuery: String, country: String): City? = withContext(Dispatchers.IO) {
        cityDao.searchExact(cityQuery, country)
    }

    suspend fun searchCountry(country: String): List<City> = withContext(Dispatchers.IO) {
        cityDao.searchCountry(country)
    }

    suspend fun getCityById(cityId: Long): City = withContext(Dispatchers.IO) {
        cityDao.getCityById(cityId)
    }
}