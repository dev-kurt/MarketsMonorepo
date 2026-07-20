package com.devkurt.markets.coin_detail.domain.impl.usecase

import com.devkurt.markets.coin_detail.domain.api.model.CoinDetail
import com.devkurt.markets.coin_detail.domain.api.repository.CoinDetailRepository
import com.devkurt.markets.coin_detail.domain.api.usecase.CoinDetailUseCase

class CoinDetailUseCaseImpl(
    private val repository: CoinDetailRepository,
) : CoinDetailUseCase {
    override suspend operator fun invoke(coinId: String): Result<CoinDetail> =
        repository.getCoinDetail(coinId)
}
