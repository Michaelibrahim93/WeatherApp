package com.test.weatherapp.ui.fragments.home

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import com.test.weatherapp.dataaccess.repository.CityRepository
import com.test.weatherapp.ui.fragments.base.WeatherBaseViewModel
import com.test.weatherapp.usecases.ToggleBookmarkUseCase
import com.test.weatherapp.vo.CityForecast

class HomeViewModel @ViewModelInject constructor(
    application: Application,
    private val cityRepository: CityRepository,
    private val toggleBookmarkUseCase: ToggleBookmarkUseCase
) : WeatherBaseViewModel(application) {

    val ldBookmarkedCities = cityRepository.loadBookmarkedCities()

    fun toggleBookmark(item: CityForecast?) = launchDataLoad {
        item?.let { toggleBookmarkUseCase.toggleBookmark(it) }
    }
}