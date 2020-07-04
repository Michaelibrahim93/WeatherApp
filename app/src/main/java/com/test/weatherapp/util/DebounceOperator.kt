package com.test.weatherapp.util

import kotlinx.coroutines.*

class DebounceOperator<T>(private val delayMs: Long = 1000L) {
    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Main)
    private var searchJob: Job? = null

    fun debounce(value: T, subscriber: (t: T) -> Unit){
        searchJob?.cancel()
        searchJob = coroutineScope.launch {
            delay(delayMs)
            subscriber(value)
        }
    }


}