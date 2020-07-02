package com.test.weatherapp.ui.fragments.base

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.test.basemodule.base.model.UiError
import com.test.basemodule.base.model.resource.Resource
import com.test.basemodule.base.viewmodel.BaseViewModel
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import java.util.concurrent.CancellationException

open class WeatherBaseViewModel(application: Application) : BaseViewModel(application) {
    protected inline fun launchDataLoad(defaultThrowableHandle: Boolean = true, crossinline block: suspend () -> Unit) {
        viewModelScope.launch {
            try {
                block()
            } catch (error: Throwable) {
                if (defaultThrowableHandle)
                    handleNetworkError(error)
            }
        }
    }

    protected inline fun<T> runResourceBlock(
        defaultThrowableHandle: Boolean = true,
        liveData: MutableLiveData<Resource<T>>,
        crossinline block: suspend () -> T) {
        viewModelScope.launch {
            try {
                liveData.value = Resource.loading(liveData.value?.data)
                val t = block()
                liveData.value = Resource.success(t)
            } catch (error: Throwable) {
                liveData.value = Resource.error(createUiErrorModel(error, false, null), liveData.value?.data)
                if (defaultThrowableHandle)
                    handleNetworkError(error)
            }
        }
    }

    override fun onCleared() {
        viewModelScope.cancel(CancellationException("ViewModel has been cleared"))
        super.onCleared()
    }

    override fun createUiErrorModel(throwable: Throwable, mustRetry: Boolean, runnable: Runnable?): UiError {
        TODO()
    }

    fun sendError(message: String) {
        ldUiError.postValue(UiError(null, message,
            mustRetry = false,
            isServerError = true,
            runnable = null
        ))
    }
}