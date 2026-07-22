package com.devkurt.markets.search.ui.impl.mapper

import com.devkurt.markets.search.domain.api.model.SearchCoin
import com.devkurt.markets.search.ui.impl.model.SearchCoinUi

fun SearchCoin.toUi(): SearchCoinUi = SearchCoinUi(
    id = id,
    name = name,
    symbol = symbol,
    marketCapRank = marketCapRank,
    imageUrl = imageUrl,
)
