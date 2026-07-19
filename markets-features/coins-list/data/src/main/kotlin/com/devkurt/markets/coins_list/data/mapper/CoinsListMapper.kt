package com.devkurt.markets.coins_list.data.mapper

import com.devkurt.markets.coins_list.data.remote.dto.CoinsListResponse
import com.devkurt.markets.coins_list.domain.api.model.CoinsList

fun CoinsListResponse.toCoinsList(): CoinsList {
    return CoinsList(
        name = data,
    )
}
