package com.test.basemodule.base.model.resource

import com.test.basemodule.base.model.UiError
import com.test.basemodule.base.model.resource.Status.*

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
</T> */
data class Resource<out T>(val status: Status, val data: T?, val error: UiError?) {
    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(SUCCESS, data, null)
        }

        fun <T> error(error: UiError?, data: T?): Resource<T> {
            return Resource(ERROR, data, error)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(LOADING, data, null)
        }
    }
}
