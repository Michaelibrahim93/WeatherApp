package com.test.weatherapp.dataaccess.repository

import android.app.Application
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.text.TextUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

class LocationRepository @Inject constructor(
    val application: Application
) {
    suspend fun geoCodeLatLng(location: Location): Address? = withContext(Dispatchers.IO){
        val geoCoder = Geocoder(application, Locale.getDefault())
        val addressList = geoCoder.getFromLocation(location.latitude, location.longitude, 10)
        if (addressList.isNullOrEmpty())
            return@withContext null
        for ( itr in addressList)
            if (!TextUtils.isEmpty(itr.locality) && !TextUtils.isEmpty(itr.countryName))
                return@withContext itr
        return@withContext addressList[0]
    }
}