package com.devkurt.markets.coin_detail.ui.api

import com.devkurt.markets.graph_list.ui.api.GraphList
import com.devkurt.markets.navigation.api.GraphMain
import kotlinx.serialization.Serializable

@Serializable
data class CoinDetailRoute(val coinId: String) : GraphList, GraphMain
