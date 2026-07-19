package com.devkurt.markets.coins_list.ui.impl.section

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import com.devkurt.markets.navigation.api.LocalGraphMain
import com.devkurt.markets.navigation.api.safePop
import com.devkurt.markets.coins_list.ui.impl.CoinsListEvent
import com.devkurt.markets.coins_list.ui.impl.CoinsListState
import com.devkurt.markets.ui.api.bars.MkCenterAlignedTopAppBar
import com.devkurt.markets.ui.api.buttons.MkIconButton
import com.devkurt.markets.ui.api.display.MkIcon
import com.devkurt.markets.ui.api.display.MkText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoinsListTopBar(
    state: CoinsListState,
    onEvent: (CoinsListEvent) -> Unit,
    scrollBehavior: TopAppBarScrollBehavior? = null,
) {
    val mainGraph = LocalGraphMain.currentOrNull
    MkCenterAlignedTopAppBar(
        title = {
            MkText(text = state.name.ifEmpty { "CoinsList" })
        },
        navigationIcon = {
            MkIconButton(
                onClick = { mainGraph?.safePop() },
            ) {
                MkIcon(Icons.AutoMirrored.Filled.ArrowBack)
            }
        },
        actions = {
            MkIconButton(
                onClick = { onEvent(CoinsListEvent.Refresh) },
            ) {
                MkIcon(Icons.Default.Refresh)
            }
        },
        scrollBehavior = scrollBehavior,
    )
}
