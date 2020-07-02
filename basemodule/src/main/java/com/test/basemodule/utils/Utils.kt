package com.test.basemodule.utils

object Utils {

    @JvmStatic
    fun equals(o1: Any?, o2: Any?, allowNull: Boolean): Boolean {
        return if (!allowNull && (o1 == null || o2 == null)) false
        else if (allowNull && o1 == null && o2 == null) true
        else if (o1 != null) o1 == o2
        else false
    }
}