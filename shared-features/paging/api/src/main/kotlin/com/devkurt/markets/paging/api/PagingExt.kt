package com.devkurt.markets.paging.api

import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

fun <T : Any> pagingFlow(
    pageSize: Int = PagingConstants.PAGE_SIZE,
    loadDataFromApi: suspend (page: Int) -> List<T>,
): Flow<PagingData<T>> = Pager(
    config = PagingConfig(
        pageSize = pageSize,
        initialLoadSize = pageSize,
        prefetchDistance = pageSize / 2,
        enablePlaceholders = false,
    ),
    pagingSourceFactory = { MkPagingSource(loadDataFromApi) },
).flow

val CombinedLoadStates.refreshError: Throwable?
    get() = (refresh as? LoadState.Error)?.error

val CombinedLoadStates.appendError: Throwable?
    get() = (append as? LoadState.Error)?.error

val CombinedLoadStates.isRefreshing: Boolean
    get() = refresh is LoadState.Loading

val CombinedLoadStates.isAppending: Boolean
    get() = append is LoadState.Loading

val CombinedLoadStates.isEmpty: Boolean
    get() = refresh is LoadState.NotLoading && append.endOfPaginationReached
