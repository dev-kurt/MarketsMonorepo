package com.devkurt.markets.coins_list.ui.impl.section

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.devkurt.markets.coins_list.ui.impl.R
import com.devkurt.markets.navigation.api.LocalGraphMain
import com.devkurt.markets.search.ui.api.SearchRoute
import com.devkurt.markets.ui.api.bars.MkCenterAlignedTopAppBar
import com.devkurt.markets.ui.api.buttons.MkIconButton
import com.devkurt.markets.ui.api.display.MkIcon
import com.devkurt.markets.ui.api.display.MkText
import com.devkurt.markets.ui.api.testing.mkTestTag

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoinsListTopBar() {
    val mainGraph = LocalGraphMain.currentOrNull

    MkCenterAlignedTopAppBar(
        title = { MkText(stringResource(R.string.coins_list_title)) },
        actions = {
            MkIconButton(
                onClick = { mainGraph?.add(SearchRoute) },
                modifier = Modifier.mkTestTag("search_open"),
            ) {
                MkIcon(Icons.Default.Search)
            }
        },
    )
}
