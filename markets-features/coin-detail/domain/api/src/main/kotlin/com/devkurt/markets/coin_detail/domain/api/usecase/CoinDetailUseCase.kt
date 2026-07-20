package com.devkurt.markets.coin_detail.domain.api.usecase

import com.devkurt.markets.coin_detail.domain.api.model.CoinDetail

interface CoinDetailUseCase {
    suspend operator fun invoke(coinId: String): Result<CoinDetail>
}
