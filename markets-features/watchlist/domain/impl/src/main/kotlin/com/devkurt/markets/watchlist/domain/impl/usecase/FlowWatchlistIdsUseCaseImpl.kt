package com.devkurt.markets.watchlist.domain.impl.usecase

import com.devkurt.markets.watchlist.domain.api.repository.WatchlistRepository
import com.devkurt.markets.watchlist.domain.api.usecase.FlowWatchlistIdsUseCase
import kotlinx.coroutines.flow.Flow

class FlowWatchlistIdsUseCaseImpl(
    private val repository: WatchlistRepository,
) : FlowWatchlistIdsUseCase {
    override operator fun invoke(): Flow<Set<String>> = repository.flowWatchlistIds()
}
