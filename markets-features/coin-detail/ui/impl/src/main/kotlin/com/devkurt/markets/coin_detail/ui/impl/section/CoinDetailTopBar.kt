package com.devkurt.markets.coin_detail.ui.impl.section

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.devkurt.markets.coin_detail.ui.impl.CoinDetailState
import com.devkurt.markets.navigation.api.LocalGraphMain
import com.devkurt.markets.navigation.api.safePop
import com.devkurt.markets.ui.api.bars.MkCenterAlignedTopAppBar
import com.devkurt.markets.ui.api.buttons.MkIconButton
import com.devkurt.markets.ui.api.display.MkIcon
import com.devkurt.markets.ui.api.display.MkText
import com.devkurt.markets.ui.api.testing.mkTestTag

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoinDetailTopBar(state: CoinDetailState) {
    val mainGraph = LocalGraphMain.currentOrNull

    MkCenterAlignedTopAppBar(
        title = { MkText(state.coin?.name.orEmpty()) },
        navigationIcon = {
            MkIconButton(
                onClick = { mainGraph?.safePop() },
                modifier = Modifier.mkTestTag("coin_detail_back"),
            ) {
                MkIcon(Icons.AutoMirrored.Filled.ArrowBack)
            }
        },
    )
}
