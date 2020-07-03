package com.test.weatherapp.dataaccess.operators

import android.app.Application
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.test.weatherapp.dataaccess.storage.dao.CityDao
import com.test.weatherapp.util.AssetsUtils
import com.test.weatherapp.vo.City
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.yield
import timber.log.Timber
import java.lang.reflect.Type
import javax.inject.Inject

class CitiesDownloader @Inject constructor(
    private val application: Application,
    private val cityDao: CityDao,
    private val sharedPreferences: SharedPreferences,
    private val gson: Gson
) {
    companion object {
        private const val KEY_PROGRESS = "KEY_PROGRESS"
        private const val CITIES_FILES_NUMBERS = 210
    }

    fun shouldStartDownloading(): Boolean {
        return getDownloadProgress() < CITIES_FILES_NUMBERS - 1
    }

    suspend fun downloadCitiesToDatabase() = withContext(Dispatchers.IO) {
        val progress = getDownloadProgress()
        for (i in progress until CITIES_FILES_NUMBERS) {
            Timber.d("progress: $i")
            val jsonString = loadCityList(i)
            val cityList = parseCities(jsonString)
            cityDao.insertAllCities(cityList)
            updateProgress(i)
            yield()
        }
        val count = cityDao.countItems()
        Timber.d("progress: $count")
    }

    private fun updateProgress(i: Int) {
        sharedPreferences.edit()
            .putInt(KEY_PROGRESS, i)
            .apply()
    }

    private fun parseCities(jsonString: String): List<City> {
        val listType: Type = object : TypeToken<List<City>>() {}.type
        return gson.fromJson(jsonString, listType)
    }

    private fun loadCityList(i: Int): String {
        return AssetsUtils.readFile(application, "cities/city_list_$i.json")!!
    }

    private fun getDownloadProgress(): Int {
        return sharedPreferences.getInt(KEY_PROGRESS, 0)
    }
}