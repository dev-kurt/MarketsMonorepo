package com.devkurt.markets.coins_list.data.repository

import com.devkurt.markets.network.api.toResult
import com.devkurt.markets.coins_list.data.mapper.toCoinsList
import com.devkurt.markets.coins_list.data.remote.api.CoinsListRemoteApi
import com.devkurt.markets.coins_list.domain.api.model.CoinsList
import com.devkurt.markets.coins_list.domain.api.repository.CoinsListRepository

class CoinsListRepositoryImpl(
    private val coinsListRemoteApi: CoinsListRemoteApi,
) : CoinsListRepository {

    override suspend fun getCoinsList(): Result<CoinsList> {
        return coinsListRemoteApi.getCoinsList().toResult()
            .map { coinsListResponse ->
                coinsListResponse.toCoinsList()
            }
    }
}
