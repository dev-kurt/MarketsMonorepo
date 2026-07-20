package com.devkurt.markets.watchlist.data.repository

import androidx.datastore.core.DataStore
import com.devkurt.markets.network.api.toResult
import com.devkurt.markets.watchlist.data.local.WatchlistData
import com.devkurt.markets.watchlist.data.mapper.toWatchlistCoin
import com.devkurt.markets.watchlist.data.remote.api.WatchlistRemoteApi
import com.devkurt.markets.watchlist.domain.api.model.WatchlistCoin
import com.devkurt.markets.watchlist.domain.api.repository.WatchlistRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class WatchlistRepositoryImpl(
    private val dataStore: DataStore<WatchlistData>,
    private val watchlistRemoteApi: WatchlistRemoteApi,
) : WatchlistRepository {

    override fun flowWatchlistIds(): Flow<Set<String>> =
        dataStore.data.map { data -> data.coinIds }

    override suspend fun toggle(coinId: String) {
        dataStore.updateData { data ->
            val updated = if (coinId in data.coinIds) {
                data.coinIds - coinId
            } else {
                data.coinIds + coinId
            }
            data.copy(coinIds = updated)
        }
    }

    override suspend fun getWatchlistCoins(ids: Set<String>): Result<List<WatchlistCoin>> {
        if (ids.isEmpty()) return Result.success(emptyList())
        return watchlistRemoteApi.getCoins(ids)
            .toResult()
            .map { coins -> coins.map { coin -> coin.toWatchlistCoin() } }
    }
}
