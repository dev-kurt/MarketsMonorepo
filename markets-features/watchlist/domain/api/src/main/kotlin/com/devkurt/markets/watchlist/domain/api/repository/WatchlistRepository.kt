package com.devkurt.markets.watchlist.domain.api.repository

import com.devkurt.markets.watchlist.domain.api.model.WatchlistCoin
import kotlinx.coroutines.flow.Flow

interface WatchlistRepository {

    fun flowWatchlistIds(): Flow<Set<String>>

    suspend fun toggle(coinId: String)

    suspend fun getWatchlistCoins(ids: Set<String>): Result<List<WatchlistCoin>>
}
