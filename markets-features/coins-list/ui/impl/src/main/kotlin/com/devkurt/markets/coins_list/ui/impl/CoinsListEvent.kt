package com.devkurt.markets.coins_list.ui.impl

sealed interface CoinsListEvent {
    data class CoinClicked(val coinId: String) : CoinsListEvent
}
