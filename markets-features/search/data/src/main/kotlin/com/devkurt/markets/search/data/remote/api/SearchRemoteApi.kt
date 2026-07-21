package com.devkurt.markets.search.data.remote.api

import com.devkurt.markets.network.api.NetworkResult
import com.devkurt.markets.network.api.safeApiCall
import com.devkurt.markets.search.data.remote.dto.SearchResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter

private const val ENDPOINT = "search"

class SearchRemoteApi(
    private val httpClient: HttpClient,
) {

    suspend fun search(query: String): NetworkResult<SearchResponse> {
        return safeApiCall {
            httpClient.get(ENDPOINT) {
                parameter("query", query)
            }
        }
    }
}
