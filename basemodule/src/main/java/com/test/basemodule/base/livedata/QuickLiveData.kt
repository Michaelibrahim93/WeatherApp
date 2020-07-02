package com.test.basemodule.base.livedata

import androidx.lifecycle.MutableLiveData

/**
 * Created by Michael on 1/4/18.
 */
class QuickLiveData<T> : MutableLiveData<T>() {
    fun postValue() {
        postValue(value)
    }
    fun setValue() {
        value = value
    }
}