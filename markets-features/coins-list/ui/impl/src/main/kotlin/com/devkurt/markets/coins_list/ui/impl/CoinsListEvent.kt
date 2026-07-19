package com.devkurt.markets.coins_list.ui.impl

sealed interface CoinsListEvent {
    data class UpdateState(
        val update: (CoinsListState) -> CoinsListState,
    ) : CoinsListEvent

    data object Refresh : CoinsListEvent
}
