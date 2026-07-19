package com.devkurt.markets.coins_list.domain.api.repository

import androidx.paging.PagingData
import com.devkurt.markets.coins_list.domain.api.model.Coin
import kotlinx.coroutines.flow.Flow

interface CoinsListRepository {

    fun getCoins(): Flow<PagingData<Coin>>
}
