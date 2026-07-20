package com.devkurt.markets.coin_detail.data.repository

import com.devkurt.markets.coin_detail.data.mapper.toCoinDetail
import com.devkurt.markets.coin_detail.data.remote.api.CoinDetailRemoteApi
import com.devkurt.markets.coin_detail.domain.api.model.CoinDetail
import com.devkurt.markets.coin_detail.domain.api.repository.CoinDetailRepository
import com.devkurt.markets.network.api.toResult

class CoinDetailRepositoryImpl(
    private val coinDetailRemoteApi: CoinDetailRemoteApi,
) : CoinDetailRepository {

    override suspend fun getCoinDetail(coinId: String): Result<CoinDetail> =
        coinDetailRemoteApi.getCoinDetail(coinId)
            .toResult()
            .map { dto -> dto.toCoinDetail() }
}
