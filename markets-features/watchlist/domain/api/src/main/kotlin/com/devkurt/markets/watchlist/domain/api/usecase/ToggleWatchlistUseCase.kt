package com.devkurt.markets.watchlist.domain.api.usecase

interface ToggleWatchlistUseCase {
    suspend operator fun invoke(coinId: String)
}
