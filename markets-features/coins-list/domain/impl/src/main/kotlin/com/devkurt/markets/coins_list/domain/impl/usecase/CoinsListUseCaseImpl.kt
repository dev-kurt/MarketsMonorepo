package com.devkurt.markets.coins_list.domain.impl.usecase

import androidx.paging.PagingData
import com.devkurt.markets.coins_list.domain.api.model.Coin
import com.devkurt.markets.coins_list.domain.api.repository.CoinsListRepository
import com.devkurt.markets.coins_list.domain.api.usecase.CoinsListUseCase
import kotlinx.coroutines.flow.Flow

class CoinsListUseCaseImpl(
    private val repository: CoinsListRepository,
) : CoinsListUseCase {
    override operator fun invoke(): Flow<PagingData<Coin>> = repository.getCoins()
}
