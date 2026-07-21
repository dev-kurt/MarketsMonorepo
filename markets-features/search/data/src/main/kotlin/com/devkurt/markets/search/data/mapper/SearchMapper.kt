package com.devkurt.markets.search.data.mapper

import com.devkurt.markets.search.data.remote.dto.SearchCoinDto
import com.devkurt.markets.search.domain.api.model.SearchCoin

fun SearchCoinDto.toSearchCoin(): SearchCoin = SearchCoin(
    id = id,
    name = name,
    symbol = symbol,
    marketCapRank = marketCapRank,
    imageUrl = large,
)
