package com.test.weatherapp.ui.fragments.home

import android.app.Application
import android.location.Address
import android.location.Location
import androidx.hilt.lifecycle.ViewModelInject
import com.test.weatherapp.dataaccess.repository.CityRepository
import com.test.weatherapp.dataaccess.repository.LocationRepository
import com.test.weatherapp.ui.fragments.base.WeatherBaseViewModel
import com.test.weatherapp.usecases.AddFirstCityUseCase
import com.test.weatherapp.usecases.ToggleBookmarkUseCase
import com.test.weatherapp.vo.CityForecast
import timber.log.Timber

class HomeViewModel @ViewModelInject constructor(
    application: Application,
    private val cityRepository: CityRepository,
    private val firstCityUseCase: AddFirstCityUseCase,
    private val toggleBookmarkUseCase: ToggleBookmarkUseCase
) : WeatherBaseViewModel(application) {

    val ldBookmarkedCities = cityRepository.loadBookmarkedCities()

    fun toggleBookmark(item: CityForecast?) = launchDataLoad {
        item?.let { toggleBookmarkUseCase.toggleBookmark(it) }
    }

    fun shouldListenToLocation(): Boolean {
        return !cityRepository.hasAddedFirstBookmark()
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