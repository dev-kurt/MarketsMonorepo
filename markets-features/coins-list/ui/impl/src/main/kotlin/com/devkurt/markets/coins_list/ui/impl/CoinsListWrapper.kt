package com.devkurt.markets.coins_list.ui.impl

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import org.koin.androidx.compose.koinViewModel

@Composable
fun CoinsListWrapper(
    viewModel: CoinsListViewModel = koinViewModel(),
) {
    val coins = viewModel.coins.collectAsLazyPagingItems()
    val watchlistIds by viewModel.watchlistIds.collectAsStateWithLifecycle()

    CoinsListScreen(
        coins = coins,
        watchlistIds = watchlistIds,
        onEvent = viewModel::onEvent,
    )
}
