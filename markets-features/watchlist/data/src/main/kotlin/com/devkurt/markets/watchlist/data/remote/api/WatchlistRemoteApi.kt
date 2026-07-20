package com.devkurt.markets.watchlist.data.remote.api

import com.devkurt.markets.network.api.NetworkResult
import com.devkurt.markets.network.api.safeApiCall
import com.devkurt.markets.watchlist.data.remote.dto.WatchlistCoinDto
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter

private const val ENDPOINT = "coins/markets"
private const val VS_CURRENCY = "usd"

class WatchlistRemoteApi(
    private val httpClient: HttpClient,
) {

    suspend fun getCoins(ids: Set<String>): NetworkResult<List<WatchlistCoinDto>> {
        return safeApiCall {
            httpClient.get(ENDPOINT) {
                parameter("vs_currency", VS_CURRENCY)
                parameter("ids", ids.joinToString(","))
                parameter("order", "market_cap_desc")
                parameter("sparkline", false)
                parameter("price_change_percentage", "24h")
            }
        }
    }
}
