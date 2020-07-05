package com.test.weatherapp.dataaccess.repository

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import com.test.basemodule.utils.CalendarUtils
import com.test.weatherapp.dataaccess.network.api.WebService
import com.test.weatherapp.dataaccess.storage.dao.CityForecastDao
import com.test.weatherapp.dataaccess.storage.dao.CityDao
import com.test.weatherapp.vo.City
import com.test.weatherapp.vo.CityForecast
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

@ActivityRetainedScoped
class ForecastRepository @Inject constructor(
    private val webService: WebService,
    private val cityDao: CityDao,
    private val cityForecastDao: CityForecastDao,
    private val sharedPreferences: SharedPreferences
){
    fun loadBookmarkedCities(): LiveData<List<CityForecast>> {
        return cityForecastDao.getBookmarkedCities()
    }

    suspend fun loadBookmarkedCitiesSync(): List<CityForecast> = withContext(Dispatchers.IO) {
        cityForecastDao.getBookmarkedCitiesSync()
    }

    suspend fun loadCityForecast(cityId: Long): CityForecast {
        val dbForecast = cityForecastDao.getForecastByCityId(cityId)
        val fetchDataFromServer = dbForecast?.lastForecastUpdate == null
                || CalendarUtils.same(CalendarUtils.CalendarUnit.DAY
                        , CalendarUtils.unixToCalendar(dbForecast.lastForecastUpdate)
                        , Calendar.getInstance())
        return if (fetchDataFromServer)
            fetWeatherForecast(cityId, dbForecast)
        else
            dbForecast!!
    }

    private suspend fun fetWeatherForecast(cityId: Long, dbForecast: CityForecast?): CityForecast {
        val city = dbForecast?.city ?: cityDao.getCityById(cityId)
        val response = webService.getWeatherForecastList(city.coord.lat, city.coord.lon)
        val updatedForecast = CityForecast.create(
            city = city,
            forecastList = response.daily,
            lastForecastUpdate = Date().time/1000,
            isBookMarked = dbForecast?.isBookMarked?:false
        )

        insertCityForecast(updatedForecast)

        return updatedForecast
    }

    suspend fun getForecastByCityId(id: Long): CityForecast? {
        return cityForecastDao.getForecastByCityId(id)
    }

    suspend fun getBookmarkedCitiesCount(): Int {
        return cityForecastDao.getBookmarkedCitiesCount()
    }

    suspend fun insertCityForecast(dbCityForecast: CityForecast) {
        cityForecastDao.insertCityForecast(dbCityForecast)
        if (dbCityForecast.isBookMarked)
            sharedPreferences.edit().putBoolean(KEY_FIRST_BOOKMARK, true).apply()
    }

    fun hasAddedFirstBookmark(): Boolean {
        return sharedPreferences.getBoolean(KEY_FIRST_BOOKMARK, false)
    }

    fun updateBookmarkedCities() {

    }

    companion object {
        private const val KEY_FIRST_BOOKMARK = "KEY_FIRST_BOOKMARK"
    }
}