package com.devkurt.markets.coins_list.ui.impl.section

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.devkurt.markets.coins_list.ui.impl.R
import com.devkurt.markets.ui.api.bars.MkCenterAlignedTopAppBar
import com.devkurt.markets.ui.api.display.MkText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoinsListTopBar() {
    MkCenterAlignedTopAppBar(
        title = { MkText(stringResource(R.string.coins_list_title)) },
    )
}
