package com.devkurt.markets.network.api

sealed interface NetworkResult<out T> {
    data class Success<T>(val data: T) : NetworkResult<T>
    data class Error(val throwable: Throwable) : NetworkResult<Nothing>
}

fun <T> NetworkResult<T>.toResult(): Result<T> = when (this) {
    is NetworkResult.Success -> Result.success(data)
    is NetworkResult.Error -> Result.failure(throwable)
}
