package com.devkurt.markets.search.ui.impl.section

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.devkurt.markets.navigation.api.LocalGraphMain
import com.devkurt.markets.navigation.api.safePop
import com.devkurt.markets.search.ui.impl.R
import com.devkurt.markets.ui.api.bars.MkCenterAlignedTopAppBar
import com.devkurt.markets.ui.api.buttons.MkIconButton
import com.devkurt.markets.ui.api.display.MkIcon
import com.devkurt.markets.ui.api.display.MkText
import com.devkurt.markets.ui.api.inputs.MkSearchBar
import com.devkurt.markets.ui.api.inputs.MkSearchBarState
import com.devkurt.markets.ui.api.testing.mkTestTag

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchTopBar(searchBarState: MkSearchBarState) {
    val mainGraph = LocalGraphMain.currentOrNull

    MkCenterAlignedTopAppBar(
        title = {
            MkSearchBar(
                state = searchBarState,
                placeholder = { MkText(stringResource(R.string.search_placeholder)) },
                modifier = Modifier.mkTestTag("search_input"),
            )
        },
        navigationIcon = {
            MkIconButton(
                onClick = { mainGraph?.safePop() },
                modifier = Modifier.mkTestTag("search_back"),
            ) {
                MkIcon(Icons.AutoMirrored.Filled.ArrowBack)
            }
        },
    )
}
