package com.test.weatherapp.dataaccess.network.response

import com.google.gson.annotations.SerializedName

class ListResponse<T> {
    var list: List<T> = ArrayList()
    @SerializedName("cnt")
    var itemsCount = 0
}