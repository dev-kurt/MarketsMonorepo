package com.devkurt.markets.search.ui.impl

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.devkurt.markets.coin_detail.ui.api.CoinDetailRoute
import com.devkurt.markets.navigation.api.LocalGraphMain
import com.devkurt.markets.search.ui.impl.section.SearchCoinRow
import com.devkurt.markets.search.ui.impl.section.SearchTopBar
import com.devkurt.markets.ui.api.feedback.MkFeedbackPlaceholder
import com.devkurt.markets.ui.api.feedback.MkFeedbackType
import com.devkurt.markets.ui.api.frame.MkScreenScaffold
import com.devkurt.markets.ui.api.inputs.MkSearchBarState
import com.devkurt.markets.ui.api.testing.mkTestTag
import com.devkurt.markets.ui.api.theme.MkTheme

@Composable
fun SearchScreen(
    state: SearchState,
    searchBarState: MkSearchBarState,
    onEvent: (SearchEvent) -> Unit,
) {
    val mainGraph = LocalGraphMain.currentOrNull

    MkScreenScaffold(
        topBar = { SearchTopBar(searchBarState) },
    ) { paddingValues ->
        when {
            state.error != null -> {
                MkFeedbackPlaceholder(
                    message = state.error,
                    type = MkFeedbackType.Error,
                    onRetry = { onEvent(SearchEvent.Retry) },
                    modifier = Modifier
                        .padding(paddingValues)
                        .padding(MkTheme.padding.md),
                )
            }

            state.results.isEmpty() && state.query.isNotBlank() && !state.isLoading -> {
                MkFeedbackPlaceholder(
                    message = stringResource(R.string.search_no_results),
                    type = MkFeedbackType.Info,
                    modifier = Modifier
                        .padding(paddingValues)
                        .padding(MkTheme.padding.md),
                )
            }

            else -> {
                LazyColumn(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxWidth()
                        .mkTestTag("search_results"),
                    contentPadding = PaddingValues(MkTheme.padding.md),
                    verticalArrangement = Arrangement.spacedBy(MkTheme.padding.sm),
                ) {
                    items(state.results, key = { it.id }) { coin ->
                        SearchCoinRow(
                            coin = coin,
                            onClick = { mainGraph?.add(CoinDetailRoute(coin.id)) },
                        )
                    }
                }
            }
        }
    }
}
