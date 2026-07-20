package com.devkurt.markets.watchlist.di

import com.devkurt.markets.dev_tools.ui.api.DevToolsAction
import com.devkurt.markets.watchlist.domain.api.repository.WatchlistRepository

class ClearWatchlistAction(
    private val repository: WatchlistRepository,
) : DevToolsAction {
    override val titleRes: Int = R.string.dev_tools_clear_watchlist
    override val successMessageRes: Int = R.string.dev_tools_watchlist_cleared

    override suspend fun execute(): Result<Unit> = runCatching {
        repository.clear()
    }
}
