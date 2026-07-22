package com.devkurt.markets.coin_detail.ui.impl

import androidx.compose.runtime.Immutable
import com.devkurt.markets.coin_detail.ui.api.CoinDetailRoute
import com.devkurt.markets.coin_detail.ui.impl.model.CoinDetailUi

@Immutable
data class CoinDetailState(
    val route: CoinDetailRoute = CoinDetailRoute(),
    val isLoading: Boolean = false,
    val coin: CoinDetailUi? = null,
    val error: String? = null,
)
