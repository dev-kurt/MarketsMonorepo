package com.devkurt.markets.coins_list.ui.impl

import androidx.compose.runtime.Composable
import androidx.paging.compose.collectAsLazyPagingItems
import org.koin.androidx.compose.koinViewModel

@Composable
fun CoinsListWrapper(
    viewModel: CoinsListViewModel = koinViewModel(),
) {
    val coins = viewModel.coins.collectAsLazyPagingItems()

    CoinsListScreen(
        coins = coins,
        onEvent = { },
    )
}
