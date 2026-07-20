package com.devkurt.markets.watchlist.domain.impl.usecase

import com.devkurt.markets.watchlist.domain.api.model.WatchlistCoin
import com.devkurt.markets.watchlist.domain.api.repository.WatchlistRepository
import com.devkurt.markets.watchlist.domain.api.usecase.GetWatchlistCoinsUseCase

class GetWatchlistCoinsUseCaseImpl(
    private val repository: WatchlistRepository,
) : GetWatchlistCoinsUseCase {
    override suspend operator fun invoke(ids: Set<String>): Result<List<WatchlistCoin>> =
        repository.getWatchlistCoins(ids)
}
