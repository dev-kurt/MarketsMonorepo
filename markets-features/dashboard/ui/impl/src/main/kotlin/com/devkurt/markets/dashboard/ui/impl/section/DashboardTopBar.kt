package com.devkurt.markets.dashboard.ui.impl.section

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.devkurt.markets.dashboard.ui.impl.R
import com.devkurt.markets.ui.api.bars.MkCenterAlignedTopAppBar
import com.devkurt.markets.ui.api.display.MkText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardTopBar() {
    MkCenterAlignedTopAppBar(
        title = { MkText(stringResource(R.string.dashboard_title)) },
    )
}
