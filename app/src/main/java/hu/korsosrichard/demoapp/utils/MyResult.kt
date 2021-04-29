package hu.korsosrichard.demoapp.utils

import java.lang.Exception

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
</T> */
data class MyResult<out T>(val status: Status, val data: T?, val exception: Exception?) {
    val isLoading: Boolean get() = status == Status.LOADING
    val isSuccess: Boolean get() = status == Status.SUCCESS
    val isFailure: Boolean get() = status == Status.ERROR

    companion object {
        fun <T> success(data: T?): MyResult<T> {
            return MyResult(Status.SUCCESS, data, null)
        }

        fun <T> failure(exception: Exception, data: T?): MyResult<T> {
            return MyResult(Status.ERROR, data, exception)
        }

        fun <T> loading(data: T?): MyResult<T> {
            return MyResult(Status.LOADING, data, null)
        }
    }
}