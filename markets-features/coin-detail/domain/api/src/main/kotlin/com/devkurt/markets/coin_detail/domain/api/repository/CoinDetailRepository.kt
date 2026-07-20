package com.devkurt.markets.coin_detail.domain.api.repository

import com.devkurt.markets.coin_detail.domain.api.model.CoinDetail

interface CoinDetailRepository {

    suspend fun getCoinDetail(coinId: String): Result<CoinDetail>
}
