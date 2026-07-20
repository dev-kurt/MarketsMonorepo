package com.devkurt.markets.coins_list.ui.impl

sealed interface CoinsListEvent {
    data class WatchlistToggled(val coinId: String) : CoinsListEvent
}
