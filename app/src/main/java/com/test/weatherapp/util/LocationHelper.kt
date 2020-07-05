package com.test.weatherapp.util

import android.annotation.SuppressLint
import android.app.Activity
import android.content.IntentSender
import android.os.Looper
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task
import timber.log.Timber
import java.lang.Exception


class LocationHelper(private val activity: Activity?, private var locationCallback: LocationCallback) {

    var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest

     init {
         fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity!!)
         fusedLocationClient.flushLocations()
    }

    @SuppressLint("MissingPermission")
    fun startListening() {
        Timber.d("startListening")
        stopListening()

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity!!)
        locationRequest = buildRequest()
        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)

        val client: SettingsClient = LocationServices.getSettingsClient(activity)
        val task: Task<LocationSettingsResponse> = client.checkLocationSettings(builder.build())

        task.addOnSuccessListener(activity) {
            Timber.d("addOnSuccessListener $it")
            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
        }

        task.addOnFailureListener(activity) {
            handleError(it)
        }
    }

    private fun handleError(it: Exception) {
        if (it is ResolvableApiException){
            try {
                it.startResolutionForResult(activity,
                    LOCATION_REQUEST)
            } catch (sendEx: IntentSender.SendIntentException) {
                // Ignore the error.
            }
        }
    }

    private fun buildRequest(): LocationRequest {
        val locationRequest = LocationRequest.create()
        locationRequest.interval = INTERVAL
        locationRequest.fastestInterval = FASTEST_INTERVAL
        locationRequest.smallestDisplacement = 100f //100
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY//set according to your app function
        return locationRequest
    }

    fun stopListening() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    companion object {
        private const val LOCATION_REQUEST = 9
        private const val INTERVAL = (10 * 1000).toLong()
        private const val FASTEST_INTERVAL = (1 * 1000).toLong()
    }

}