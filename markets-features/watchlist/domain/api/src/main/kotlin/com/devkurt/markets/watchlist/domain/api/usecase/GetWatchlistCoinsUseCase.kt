package com.devkurt.markets.watchlist.domain.api.usecase

import com.devkurt.markets.watchlist.domain.api.model.WatchlistCoin

interface GetWatchlistCoinsUseCase {
    suspend operator fun invoke(ids: Set<String>): Result<List<WatchlistCoin>>
}
