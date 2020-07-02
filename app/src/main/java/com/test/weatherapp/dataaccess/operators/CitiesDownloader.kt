package com.test.weatherapp.dataaccess.operators

import android.content.SharedPreferences
import com.test.weatherapp.dataaccess.storage.dao.CityDao
import javax.inject.Inject

class CitiesDownloader @Inject constructor(
    private val cityDao: CityDao,
    private val sharedPreferences: SharedPreferences
) {

}