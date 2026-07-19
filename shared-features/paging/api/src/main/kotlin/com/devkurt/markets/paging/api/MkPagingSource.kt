package com.devkurt.markets.paging.api

import androidx.paging.PagingSource
import androidx.paging.PagingState

object PagingConstants {
    const val STARTING_PAGE_INDEX = 1
    const val PAGE_SIZE = 20
}

internal class MkPagingSource<T : Any>(
    private val loadDataFromApi: suspend (page: Int) -> List<T>,
) : PagingSource<Int, T>() {

    override fun getRefreshKey(state: PagingState<Int, T>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val anchorPage = state.closestPageToPosition(anchorPosition) ?: return null
        return anchorPage.prevKey?.plus(1) ?: anchorPage.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        val page = params.key ?: PagingConstants.STARTING_PAGE_INDEX
        return runCatching {
            val data = loadDataFromApi(page)
            LoadResult.Page(
                data = data,
                prevKey = if (page == PagingConstants.STARTING_PAGE_INDEX) null else page - 1,
                nextKey = if (data.size < params.loadSize) null else page + 1,
            )
        }.getOrElse { throwable ->
            LoadResult.Error(throwable)
        }
    }
}
