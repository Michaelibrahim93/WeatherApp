package com.test.weatherapp.ui.fragments.splash

import android.app.Application
import android.os.Handler
import androidx.annotation.MainThread
import androidx.hilt.lifecycle.ViewModelInject
import com.test.weatherapp.dataaccess.operators.CitiesDownloader
import com.test.weatherapp.ui.fragments.base.WeatherBaseViewModel
import com.test.weatherapp.vo.City
import timber.log.Timber

class SplashViewModel @ViewModelInject constructor(
    application: Application,
    private val citiesDownloader: CitiesDownloader
): WeatherBaseViewModel(application) {
    var loadingStatus: Status = Status.LOADING

    init {
        Timber.d("init")
        Handler().postDelayed( { updateLoadingStatus(Status.INTERVAL_FINISHED) } ,3000)

        loadCitiesIfNeeded()
    }

    @MainThread
    private fun updateLoadingStatus(statusUpdate: Status) {

        Timber.d("updateLoadingStatus: $statusUpdate")

        loadingStatus = if (loadingStatus == Status.LOADING)
            statusUpdate
        else
            Status.SUCCESS

        if (loadingStatus == Status.SUCCESS)
            addAction(SplashFragment.ACTION_TO_HOME)
    }

    private fun loadCitiesIfNeeded() = launchDataLoad {
        if (citiesDownloader.shouldStartDownloading()) {
            addLoadingObject(SplashFragment.LOADING_OVERLAY, City::class, false)
            citiesDownloader.downloadCitiesToDatabase()
            removeLoadingObject(City::class)
        }

        updateLoadingStatus(Status.DATA_LOADED)
    }

    enum class Status {
        LOADING,
        INTERVAL_FINISHED,
        DATA_LOADED,
        SUCCESS
    }
}