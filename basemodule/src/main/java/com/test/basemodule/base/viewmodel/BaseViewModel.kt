package com.test.basemodule.base.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.test.basemodule.base.livedata.QuickLiveData
import com.test.basemodule.base.model.LoadingState
import com.test.basemodule.base.model.UiError
import com.test.basemodule.base.model.VMNotification
import com.test.basemodule.utils.LogUtils
import java.util.*

abstract class BaseViewModel(application: Application) : AndroidViewModel(application) {
    val ldLoadingState: QuickLiveData<LoadingState> = QuickLiveData()
    val ldActions: QuickLiveData<MutableSet<VMNotification>> = QuickLiveData()
    val ldUiError: MutableLiveData<UiError> = MutableLiveData<UiError>()

    init {
        ldActions.value = HashSet()
        ldLoadingState.value = LoadingState()
    }

    protected fun getContext(): Context {
        return getApplication()
    }

    protected fun addLoadingObject(loadingMode: Int, tag: Any) {
        addLoadingObject(loadingMode, tag, true)
    }

    protected fun addLoadingObject(
        loadingMode: Int,
        tag: Any,
        threadSafe: Boolean
    ) {
        ldLoadingState.value!!.addLoadingObject(loadingMode, tag)
        if (threadSafe) ldLoadingState.postValue() else ldLoadingState.setValue()
    }

    protected fun removeLoadingObject(tag: Any?, threadSafe: Boolean = true) {
        ldLoadingState.value!!.removeLoadingObject(tag)
        if (threadSafe)
            ldLoadingState.postValue()
        else
            ldLoadingState.setValue()
    }

    protected fun addAction(action: String?, tag: Any? = null, threadSafe: Boolean = true) {
        ldActions.value!!.add(VMNotification(action!!, tag))

        if (threadSafe) ldActions.postValue()
        else ldActions.setValue()
    }

    fun clearActions() {
        ldActions.value!!.clear()
    }

    fun handleNetworkError(
        throwable: Throwable,
        mustRetry: Boolean,
        runnable: Runnable?
    ) {
        LogUtils.w("handleNetworkError", throwable)
        ldUiError.postValue(createUiErrorModel(throwable, mustRetry, runnable))
    }

    open fun createUiErrorModel(throwable: Throwable, mustRetry: Boolean,runnable: Runnable?): UiError {
        return UiError(
            throwable, throwable.toString(), mustRetry
            , false
            , runnable
        )
    }

    fun handleNetworkError(throwable: Throwable) {
        handleNetworkError(throwable, false, null)
    }

    fun clearUiError() {
        ldUiError.postValue(null)
    }
}