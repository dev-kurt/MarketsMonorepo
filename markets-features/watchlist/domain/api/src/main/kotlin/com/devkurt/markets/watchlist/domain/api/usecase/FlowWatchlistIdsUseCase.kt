package com.devkurt.markets.watchlist.domain.api.usecase

import kotlinx.coroutines.flow.Flow

interface FlowWatchlistIdsUseCase {
    operator fun invoke(): Flow<Set<String>>
}
