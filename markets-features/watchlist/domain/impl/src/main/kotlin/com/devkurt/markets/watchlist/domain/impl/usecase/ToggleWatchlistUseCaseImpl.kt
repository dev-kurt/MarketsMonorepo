package com.devkurt.markets.watchlist.domain.impl.usecase

import com.devkurt.markets.watchlist.domain.api.repository.WatchlistRepository
import com.devkurt.markets.watchlist.domain.api.usecase.ToggleWatchlistUseCase

class ToggleWatchlistUseCaseImpl(
    private val repository: WatchlistRepository,
) : ToggleWatchlistUseCase {
    override suspend operator fun invoke(coinId: String) = repository.toggle(coinId)
}
