package com.devkurt.markets.coins_list.ui.impl

import androidx.compose.runtime.Composable
import androidx.paging.compose.collectAsLazyPagingItems
import com.devkurt.markets.coin_detail.ui.api.CoinDetailRoute
import com.devkurt.markets.graph_list.ui.api.LocalGraphList
import org.koin.androidx.compose.koinViewModel

@Composable
fun CoinsListWrapper(
    viewModel: CoinsListViewModel = koinViewModel(),
) {
    val coins = viewModel.coins.collectAsLazyPagingItems()
    val listGraph = LocalGraphList.current

    CoinsListScreen(
        coins = coins,
        onEvent = { event ->
            when (event) {
                is CoinsListEvent.CoinClicked -> listGraph.add(CoinDetailRoute(event.coinId))
            }
        },
    )
}
