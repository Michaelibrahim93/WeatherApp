package com.test.basemodule.base.model

class UiError {
    var throwable: Throwable? = null
    var errorText: String = ""
    var mustRetry = false
    var isServerError = false
    var runnable: Runnable? = null

    constructor(
        throwable: Throwable?,
        errorText: String,
        mustRetry: Boolean,
        isServerError: Boolean,
        runnable: Runnable?
    ) {
        this.throwable = throwable
        this.errorText = errorText
        this.mustRetry = mustRetry
        this.isServerError = isServerError
        this.runnable = runnable
    }

    constructor() {}
}