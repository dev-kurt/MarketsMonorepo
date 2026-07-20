package com.devkurt.markets.coin_detail.ui.impl

sealed interface CoinDetailEvent {
    data object Retry : CoinDetailEvent
    data object BackClicked : CoinDetailEvent
}
