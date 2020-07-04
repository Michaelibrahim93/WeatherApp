package com.test.weatherapp.ui.fragments.home

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.test.weatherapp.dataaccess.operators.SearchOperator
import com.test.weatherapp.dataaccess.repository.CityRepository
import com.test.weatherapp.ui.fragments.base.WeatherBaseViewModel
import com.test.weatherapp.util.DebounceOperator
import com.test.weatherapp.vo.CityForecast

class HomeViewModel @ViewModelInject constructor(
    application: Application,
    private val cityRepository: CityRepository
) : WeatherBaseViewModel(application) {

    private val ldBookmarkedCities = cityRepository.loadBookmarkedCities()

    fun toggleBookmark(item: CityForecast?) = launchDataLoad {
        item?.let { cityRepository.toggleBookmark(it) }
    }
}