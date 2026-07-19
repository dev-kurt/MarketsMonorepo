package com.devkurt.markets.coins_list.domain.api.usecase

import androidx.paging.PagingData
import com.devkurt.markets.coins_list.domain.api.model.Coin
import kotlinx.coroutines.flow.Flow

interface CoinsListUseCase {
    operator fun invoke(): Flow<PagingData<Coin>>
}
