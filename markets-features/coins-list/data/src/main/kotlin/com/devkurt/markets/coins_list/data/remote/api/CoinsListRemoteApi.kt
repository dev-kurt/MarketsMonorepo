package com.devkurt.markets.coins_list.data.remote.api

import com.devkurt.markets.coins_list.data.remote.dto.CoinMarketDto
import com.devkurt.markets.network.api.NetworkResult
import com.devkurt.markets.network.api.safeApiCall
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter

private const val ENDPOINT = "coins/markets"
private const val VS_CURRENCY = "usd"
private const val ORDER = "market_cap_desc"
private const val PRICE_CHANGE_PERCENTAGE = "24h"

class CoinsListRemoteApi(
    private val httpClient: HttpClient,
) {

    suspend fun getCoins(page: Int, perPage: Int): NetworkResult<List<CoinMarketDto>> {
        return safeApiCall {
            httpClient.get(ENDPOINT) {
                parameter("vs_currency", VS_CURRENCY)
                parameter("order", ORDER)
                parameter("per_page", perPage)
                parameter("page", page)
                parameter("sparkline", false)
                parameter("price_change_percentage", PRICE_CHANGE_PERCENTAGE)
            }
        }
    }
}
