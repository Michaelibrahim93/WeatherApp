package com.test.basemodule.base.model

class VMNotification {
    private val action: String
    private var tag: Any? = null

    constructor(action: String) {
        this.action = action
    }

    constructor(action: String, tag: Any?) {
        this.action = action
        this.tag = tag
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> getTag(tClass: Class<T>): T? {
        return if (tag != null && (tag!!.javaClass == tClass || tag!!.javaClass.isAssignableFrom(
                tClass
            ))
        ) tag as T else null
    }

    fun getAction(): String? {
        return action
    }

    fun getTag(): Any? {
        return tag
    }
}