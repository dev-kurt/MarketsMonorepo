package com.devkurt.markets.coins_list.data.remote.api

import com.devkurt.markets.network.api.NetworkResult
import com.devkurt.markets.network.api.safeApiCall
import com.devkurt.markets.coins_list.data.remote.dto.CoinsListResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.get

private const val ENDPOINT = "xx"

class CoinsListRemoteApi(
    private val httpClient: HttpClient,
) {

    suspend fun getCoinsList(): NetworkResult<CoinsListResponse> {
        return safeApiCall {
            httpClient.get(ENDPOINT)
        }
    }
}
