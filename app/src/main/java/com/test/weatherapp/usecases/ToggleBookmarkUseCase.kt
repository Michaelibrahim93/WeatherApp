package com.test.weatherapp.usecases

import android.app.Application
import com.test.weatherapp.R
import com.test.weatherapp.dataaccess.repository.CityRepository
import com.test.weatherapp.vo.CityForecast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ToggleBookmarkUseCase @Inject constructor(
    val application: Application,
    val cityRepository: CityRepository
) {
    suspend fun toggleBookmark(cityForecast: CityForecast) = withContext(Dispatchers.IO) {
        val bookmarksCount = cityRepository.getBookmarkedCitiesCount()
        if (!cityForecast.isBookMarked && bookmarksCount >= 5)
            throw Throwable(application.getString(R.string.already_5_cities_bookmarked))
        var dbCityForecast = cityRepository.getForecastByCityId(cityForecast.id)
        if (dbCityForecast == null)
            dbCityForecast = CityForecast.create(cityForecast)

        dbCityForecast.isBookMarked = !cityForecast.isBookMarked
        cityRepository.insertCityForecast(dbCityForecast)
    }
}