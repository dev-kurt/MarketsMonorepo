package com.devkurt.markets.coins_list.ui.impl

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.paging.compose.LazyPagingItems
import com.devkurt.markets.coins_list.domain.api.model.Coin
import com.devkurt.markets.coins_list.ui.impl.section.CoinRow
import com.devkurt.markets.paging.api.appendError
import com.devkurt.markets.paging.api.isAppending
import com.devkurt.markets.paging.api.isRefreshing
import com.devkurt.markets.paging.api.refreshError
import com.devkurt.markets.ui.api.bars.MkCenterAlignedTopAppBar
import com.devkurt.markets.ui.api.buttons.MkTextButton
import com.devkurt.markets.ui.api.display.MkText
import com.devkurt.markets.ui.api.feedback.MkCircularProgressIndicator
import com.devkurt.markets.ui.api.feedback.MkError
import com.devkurt.markets.ui.api.frame.MkScreenScaffold
import com.devkurt.markets.ui.api.theme.MkTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoinsListScreen(
    coins: LazyPagingItems<Coin>,
    onEvent: (CoinsListEvent) -> Unit,
) {
    val loadState = coins.loadState

    MkScreenScaffold(
        topBar = {
            MkCenterAlignedTopAppBar(
                title = { MkText("Markets") },
            )
        },
        isLoading = loadState.isRefreshing && coins.itemCount > 0,
        onRefresh = { coins.refresh() },
    ) { paddingValues ->
        val refreshError = loadState.refreshError

        when {
            refreshError != null && coins.itemCount == 0 -> {
                MkError(
                    message = refreshError.message ?: "Something went wrong",
                    action = {
                        MkTextButton(onClick = { coins.retry() }) {
                            MkText("Retry")
                        }
                    },
                )
            }

            loadState.isRefreshing && coins.itemCount == 0 -> {
                Box(
                    modifier = Modifier.padding(paddingValues),
                    contentAlignment = Alignment.Center,
                ) {
                    MkCircularProgressIndicator()
                }
            }

            else -> {
                LazyColumn(
                    modifier = Modifier.padding(paddingValues),
                    contentPadding = androidx.compose.foundation.layout.PaddingValues(
                        MkTheme.padding.md,
                    ),
                    verticalArrangement = Arrangement.spacedBy(MkTheme.padding.sm),
                ) {
                    items(
                        count = coins.itemCount,
                        key = { index -> coins.peek(index)?.id ?: index },
                    ) { index ->
                        coins[index]?.let { coin ->
                            CoinRow(
                                coin = coin,
                                onClick = { onEvent(CoinsListEvent.CoinClicked(coin.id)) },
                            )
                        }
                    }

                    if (loadState.isAppending) {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(MkTheme.padding.md),
                                contentAlignment = Alignment.Center,
                            ) {
                                MkCircularProgressIndicator()
                            }
                        }
                    }

                    loadState.appendError?.let { error ->
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(MkTheme.padding.md),
                                contentAlignment = Alignment.Center,
                            ) {
                                MkTextButton(onClick = { coins.retry() }) {
                                    MkText(error.message ?: "Retry")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
