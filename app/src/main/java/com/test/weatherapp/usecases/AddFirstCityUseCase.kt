package com.test.weatherapp.usecases

import android.location.Location
import com.test.weatherapp.dataaccess.repository.CityRepository
import com.test.weatherapp.dataaccess.repository.LocationRepository
import com.test.weatherapp.vo.City
import com.test.weatherapp.vo.CityForecast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject
import kotlin.math.pow

class AddFirstCityUseCase @Inject constructor(
    private val cityRepository: CityRepository,
    private val locationRepository: LocationRepository
) {
    suspend fun addFirstCity(location: Location?) = withContext(Dispatchers.IO){
        val address = location?.let { locationRepository.geoCodeLatLng(it) }
        Timber.d("address: $address")

        val cityName = address?.adminArea?: DEFAULT_CITY
        val countryName = address?.countryCode?: DEFAULT_COUNTRY
        Timber.d("address: $cityName $countryName")

        var city = cityRepository.searchExact(cityName, countryName)
        Timber.d("searchExact: ${city?.name}")

        if (city == null){
            val list = cityRepository.searchCountry(countryName)
            city = list.minBy { calcDistance(location!!, it) }
            Timber.d("searchCountry: ${city?.name}")
        }

        city?.let {
            cityRepository.insertCityForecast(CityForecast.create(
                city = it,
                isBookMarked = true
            ))
            Timber.d("insertCityForecast: ${city.name}")
        }
    }

    private fun calcDistance(location: Location, it: City): Double {
        return (location.latitude - it.coord.lat).pow(2.0) + (location.longitude - it.coord.lon).pow(2.0)
    }

    companion object {
        const val DEFAULT_CITY = "London"
        const val DEFAULT_COUNTRY = "GB"
    }
}