package com.example.beers_of_the_world

import okhttp3.ResponseBody

class Resource<T> {

    sealed class Resource<out T> {
        data class Success<out T>(val value: T) : Resource<T>()
        data class Failure(
            val isNetworkError: Boolean,
            val errorCode: Int?,
            val errorBody: ResponseBody?,
            val unknownError: Boolean = false
        ) : Resource<Nothing>()

        object Loading : Resource<Nothing>()
    }
}