package com.test.weatherapp.ui.fragments.home

import android.app.Application
import android.location.Location
import androidx.hilt.lifecycle.ViewModelInject
import com.test.weatherapp.dataaccess.repository.ForecastRepository
import com.test.weatherapp.ui.fragments.base.WeatherBaseViewModel
import com.test.weatherapp.usecases.AddFirstCityUseCase
import com.test.weatherapp.usecases.ToggleBookmarkUseCase
import com.test.weatherapp.vo.CityForecast
import timber.log.Timber

class HomeViewModel @ViewModelInject constructor(
    application: Application,
    private val forecastRepository: ForecastRepository,
    private val firstCityUseCase: AddFirstCityUseCase,
    private val toggleBookmarkUseCase: ToggleBookmarkUseCase
) : WeatherBaseViewModel(application) {

    val ldBookmarkedCities = forecastRepository.loadBookmarkedCities()

    init {
        updateBookmarkedCities()
    }

    private fun updateBookmarkedCities() = launchDataLoad(false) {
        forecastRepository.updateBookmarkedCities()
    }

    fun toggleBookmark(item: CityForecast?) = launchDataLoad {
        item?.let { toggleBookmarkUseCase.toggleBookmark(it) }
    }

    fun shouldListenToLocation(): Boolean {
        return !forecastRepository.hasAddedFirstBookmark()
    }

    fun addCurrentCityToBookmarks(location: Location) = launchDataLoad {
        Timber.d("location: $location")
        firstCityUseCase.addFirstCity(location)
    }

    fun addDefaultCity() = launchDataLoad{
        Timber.d("location: addDefaultCity")
        firstCityUseCase.addFirstCity(null)
    }
}