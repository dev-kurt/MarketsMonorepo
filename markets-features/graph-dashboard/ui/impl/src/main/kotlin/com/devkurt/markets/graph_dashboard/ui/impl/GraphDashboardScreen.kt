package com.devkurt.markets.graph_dashboard.ui.impl

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.devkurt.markets.graph_dashboard.ui.api.LocalGraphDashboard
import org.koin.androidx.compose.koinViewModel

@Composable
fun GraphDashboardScreen(
    viewModel: GraphDashboardViewModel = koinViewModel(),
) {
    val dashboardNavGraph = LocalGraphDashboard.current
    NavDisplay(
        backStack = dashboardNavGraph,
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
