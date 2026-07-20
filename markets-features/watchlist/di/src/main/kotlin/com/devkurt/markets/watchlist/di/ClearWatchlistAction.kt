package com.devkurt.markets.watchlist.di

import com.devkurt.markets.dev_tools.ui.api.DevToolsAction
import com.devkurt.markets.watchlist.domain.api.repository.WatchlistRepository

class ClearWatchlistAction(
    private val repository: WatchlistRepository,
) : DevToolsAction {
    override val title: String = "Clear watchlist"

    override suspend fun execute(): Result<String> = runCatching {
        repository.clear()
        "Watchlist cleared"
    }
}
