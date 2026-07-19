package com.devkurt.markets.coins_list.data.repository

import androidx.paging.PagingData
import com.devkurt.markets.coins_list.data.mapper.toCoin
import com.devkurt.markets.coins_list.data.remote.api.CoinsListRemoteApi
import com.devkurt.markets.coins_list.domain.api.model.Coin
import com.devkurt.markets.coins_list.domain.api.repository.CoinsListRepository
import com.devkurt.markets.network.api.toResult
import com.devkurt.markets.paging.api.PagingConstants
import com.devkurt.markets.paging.api.pagingFlow
import kotlinx.coroutines.flow.Flow

class CoinsListRepositoryImpl(
    private val coinsListRemoteApi: CoinsListRemoteApi,
) : CoinsListRepository {

    override fun getCoins(): Flow<PagingData<Coin>> =
        pagingFlow(pageSize = PagingConstants.PAGE_SIZE) { page ->
            coinsListRemoteApi
                .getCoins(page = page, perPage = PagingConstants.PAGE_SIZE)
                .toResult()
                .map { coins -> coins.map { coin -> coin.toCoin() } }
                .getOrThrow()
        }
}
