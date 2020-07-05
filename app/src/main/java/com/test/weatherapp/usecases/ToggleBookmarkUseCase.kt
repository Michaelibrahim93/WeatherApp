package com.test.weatherapp.usecases

import android.app.Application
import com.test.weatherapp.R
import com.test.weatherapp.dataaccess.repository.ForecastRepository
import com.test.weatherapp.vo.CityForecast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class ToggleBookmarkUseCase @Inject constructor(
    val application: Application,
    val forecastRepository: ForecastRepository
) {
    suspend fun toggleBookmark(cityForecast: CityForecast) = withContext(Dispatchers.IO) {
        val bookmarksCount = forecastRepository.getBookmarkedCitiesCount()
        if (!cityForecast.isBookMarked && bookmarksCount >= 5)
            throw Throwable(application.getString(R.string.already_5_cities_bookmarked))
        var dbCityForecast = forecastRepository.getForecastByCityId(cityForecast.id)
        if (dbCityForecast == null)
            dbCityForecast = CityForecast.create(cityForecast.city)

        dbCityForecast.isBookMarked = !cityForecast.isBookMarked
        forecastRepository.insertCityForecast(dbCityForecast)
        try {
            if (dbCityForecast.isBookMarked)
                forecastRepository.updateCityForecast(dbCityForecast.id)
        }catch (t: Throwable) {
            Timber.w(t)
        }
    }
}