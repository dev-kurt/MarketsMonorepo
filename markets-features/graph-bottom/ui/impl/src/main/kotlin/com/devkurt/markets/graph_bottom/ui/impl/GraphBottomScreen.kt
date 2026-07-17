package com.devkurt.markets.graph_bottom.ui.impl

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.devkurt.markets.graph_bottom.ui.api.LocalGraphBottom
import com.devkurt.markets.graph_bottom.ui.impl.section.BottomBarSection
import com.devkurt.markets.ui.api.bars.MkShortNavigationBar
import com.devkurt.markets.ui.api.frame.MkScreenScaffold
import org.koin.androidx.compose.koinViewModel

@Composable
fun GraphBottomScreen(
    viewModel: GraphBottomViewModel = koinViewModel(),
) {
    val bottomNavGraph = LocalGraphBottom.current
    MkScreenScaffold(
        bottomBar = {
            MkShortNavigationBar {
                BottomBarSection()
            }
        },
    ) { paddingValues ->
        NavDisplay(
            backStack = bottomNavGraph,
            modifier = Modifier.padding(paddingValues),
            entryDecorators = listOf(
                rememberSaveableStateHolderNavEntryDecorator(),
                rememberViewModelStoreNavEntryDecorator(),
            ),
            entryProvider = entryProvider {
                viewModel.entryProviders.forEach { provider ->
                    with(provider) { install() }
                }
            },
        )
    }
}
