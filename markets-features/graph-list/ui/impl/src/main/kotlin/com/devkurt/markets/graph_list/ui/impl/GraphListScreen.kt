package com.devkurt.markets.graph_list.ui.impl

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.devkurt.markets.graph_list.ui.api.LocalGraphList
import org.koin.androidx.compose.koinViewModel

@Composable
fun GraphListScreen(
    viewModel: GraphListViewModel = koinViewModel(),
) {
    val listNavGraph = LocalGraphList.current
    NavDisplay(
        backStack = listNavGraph,
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
