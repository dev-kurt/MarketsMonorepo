package com.devkurt.markets.coin_detail.ui.impl

import androidx.compose.runtime.Immutable
import com.devkurt.markets.coin_detail.domain.api.model.CoinDetail

@Immutable
data class CoinDetailState(
    val isLoading: Boolean = false,
    val coin: CoinDetail? = null,
    val error: String? = null,
)
