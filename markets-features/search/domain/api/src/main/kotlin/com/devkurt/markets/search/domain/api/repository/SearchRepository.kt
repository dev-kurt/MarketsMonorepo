package com.devkurt.markets.search.domain.api.repository

import com.devkurt.markets.search.domain.api.model.SearchCoin

interface SearchRepository {

    suspend fun search(query: String): Result<List<SearchCoin>>
}
