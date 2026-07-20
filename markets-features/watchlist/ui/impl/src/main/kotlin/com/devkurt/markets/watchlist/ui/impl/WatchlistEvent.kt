package com.devkurt.markets.watchlist.ui.impl

sealed interface WatchlistEvent {
    data class RemoveClicked(val coinId: String) : WatchlistEvent
    data object Retry : WatchlistEvent
}
