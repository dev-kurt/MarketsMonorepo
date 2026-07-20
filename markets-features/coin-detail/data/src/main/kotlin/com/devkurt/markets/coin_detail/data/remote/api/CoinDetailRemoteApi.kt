package com.devkurt.markets.coin_detail.data.remote.api

import com.devkurt.markets.coin_detail.data.remote.dto.CoinDetailDto
import com.devkurt.markets.network.api.NetworkResult
import com.devkurt.markets.network.api.safeApiCall
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter

private const val ENDPOINT = "coins"

class CoinDetailRemoteApi(
    private val httpClient: HttpClient,
) {

    suspend fun getCoinDetail(coinId: String): NetworkResult<CoinDetailDto> {
        return safeApiCall {
            httpClient.get("$ENDPOINT/$coinId") {
                parameter("localization", false)
                parameter("tickers", false)
                parameter("market_data", true)
                parameter("community_data", false)
                parameter("developer_data", false)
                parameter("sparkline", false)
            }
        }
    }
}
