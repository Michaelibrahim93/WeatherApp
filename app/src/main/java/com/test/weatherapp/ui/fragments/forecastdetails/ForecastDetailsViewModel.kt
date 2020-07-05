package com.test.weatherapp.ui.fragments.forecastdetails

import android.app.Application
import android.os.Bundle
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import com.test.basemodule.base.model.resource.Resource
import com.test.basemodule.utils.CalendarUtils
import com.test.weatherapp.dataaccess.repository.CityRepository
import com.test.weatherapp.dataaccess.repository.ForecastRepository
import com.test.weatherapp.ui.fragments.base.WeatherBaseViewModel
import com.test.weatherapp.usecases.ToggleBookmarkUseCase
import com.test.weatherapp.vo.City
import com.test.weatherapp.vo.CityForecast
import com.test.weatherapp.vo.WeatherForecast
import java.util.*
import kotlin.collections.ArrayList

class ForecastDetailsViewModel @ViewModelInject constructor(
    application: Application,
    private val forecastRepository: ForecastRepository,
    private val cityRepo: CityRepository,
    private val toggleBookmarkUseCase: ToggleBookmarkUseCase
) : WeatherBaseViewModel(application) {

    val ldCity = MutableLiveData<City>()
    val ldIsBookmarked = MutableLiveData<Boolean>()
    val ldDaysForecast = MutableLiveData<Resource<List<WeatherForecast>>>()
    private var cityId: Long = 0
    var cityForecast: CityForecast? = null

    fun extractArgument(arguments: Bundle?) {
        cityId = arguments!![ForecastDetailsFragment.KEY_CITY_ID] as Long
        loadForecast()
    }

    fun loadForecast() = launchResourceDataLoad(false, ldDaysForecast){
        val city = cityRepo.getCityById(cityId)
        ldCity.value = city
        cityForecast = forecastRepository.updateCityForecast(cityId)
        ldIsBookmarked.value = cityForecast!!.isBookMarked
        toWeatherList(cityForecast!!)
    }

    private fun toWeatherList(forecast: CityForecast): List<WeatherForecast> {
        val calendar = Calendar.getInstance()
        val neededDays = ArrayList<Int>()
        for (i in 0  until 5) {
            neededDays.add(calendar[Calendar.DAY_OF_YEAR])
            calendar.add(Calendar.DAY_OF_YEAR, 1)
        }

        return forecast.forecastList!!.filter {
            val itrCal = CalendarUtils.unixToCalendar(it.unixTime)
            neededDays.contains(itrCal[Calendar.DAY_OF_YEAR])
        }
    }

    fun toggleBookmark() = launchDataLoad {
        cityForecast?.let {
            toggleBookmarkUseCase.toggleBookmark(it)
            ldIsBookmarked.value = !it.isBookMarked
            it.isBookMarked = !it.isBookMarked
        }
    }
}