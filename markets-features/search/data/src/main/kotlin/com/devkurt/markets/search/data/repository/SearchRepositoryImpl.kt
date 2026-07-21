package com.devkurt.markets.search.data.repository

import com.devkurt.markets.network.api.toResult
import com.devkurt.markets.search.data.mapper.toSearchCoin
import com.devkurt.markets.search.data.remote.api.SearchRemoteApi
import com.devkurt.markets.search.domain.api.model.SearchCoin
import com.devkurt.markets.search.domain.api.repository.SearchRepository

class SearchRepositoryImpl(
    private val searchRemoteApi: SearchRemoteApi,
) : SearchRepository {

    override suspend fun search(query: String): Result<List<SearchCoin>> {
        if (query.isBlank()) return Result.success(emptyList())
        return searchRemoteApi.search(query)
            .toResult()
            .map { response -> response.coins.map { coin -> coin.toSearchCoin() } }
    }
}
