package com.devkurt.markets.coin_detail.ui.api

import com.devkurt.markets.navigation.api.GraphMain
import kotlinx.serialization.Serializable

@Serializable
data class CoinDetailRoute(val coinId: String = "") : GraphMain
