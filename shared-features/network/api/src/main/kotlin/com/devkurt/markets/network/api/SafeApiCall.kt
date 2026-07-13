package com.devkurt.markets.network.api

import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import kotlin.coroutines.cancellation.CancellationException

suspend inline fun <reified T> safeApiCall(
    crossinline block: suspend () -> HttpResponse,
): NetworkResult<T> = try {
    NetworkResult.Success(block().body())
} catch (e: CancellationException) {
    throw e
} catch (e: Exception) {
    NetworkResult.Error(e)
}
