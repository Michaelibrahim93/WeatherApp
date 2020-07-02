package com.test.basemodule.base.model.resource

data class UserResource<T>(val status: UserStatus, val data: T?, val errors: List<String>?){
    companion object {
        fun <T> success(data: T?): UserResource<T> {
            return UserResource(UserStatus.SUCCESS, data, null)
        }

        fun <T> error(errors: List<String> , data: T?): UserResource<T> {
            return UserResource(UserStatus.ERROR, data, errors)
        }

        fun <T> loading(data: T?): UserResource<T> {
            return UserResource(UserStatus.LOADING, data, null)
        }

        fun <T> loggedOut(): UserResource<T> {
            return UserResource(UserStatus.LOGGED_OUT, null, null)
        }
    }
}