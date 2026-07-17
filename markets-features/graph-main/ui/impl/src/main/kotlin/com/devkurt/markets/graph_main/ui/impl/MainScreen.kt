package com.devkurt.markets.graph_main.ui.impl

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.devkurt.markets.graph_bottom.ui.api.GraphBottom
import com.devkurt.markets.graph_bottom.ui.api.GraphBottomRoute
import com.devkurt.markets.graph_bottom.ui.api.LocalGraphBottom
import com.devkurt.markets.graph_dashboard.ui.api.DashboardPlaceholderRoute
import com.devkurt.markets.graph_dashboard.ui.api.GraphDashboard
import com.devkurt.markets.graph_dashboard.ui.api.GraphDashboardRoute
import com.devkurt.markets.graph_dashboard.ui.api.LocalGraphDashboard
import com.devkurt.markets.navigation.api.GraphMain
import com.devkurt.markets.navigation.api.LocalGraphMain
import com.devkurt.markets.navigation.api.LocalSignalBus
import com.devkurt.markets.navigation.api.SignalBus
import com.devkurt.markets.navigation.api.rememberNavBackStack
import com.devkurt.markets.navigation.api.safePop
import com.devkurt.markets.ui.api.theme.MarketsTheme
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@Composable
fun MainScreen(
    viewModel: GraphMainViewModel = koinViewModel(),
) {
    val graphMain = rememberNavBackStack<GraphMain>(GraphBottomRoute)
    val graphBottom = rememberNavBackStack<GraphBottom>(GraphDashboardRoute)
    val graphDashboard = rememberNavBackStack<GraphDashboard>(DashboardPlaceholderRoute)
    val signalBus = koinInject<SignalBus>()

    CompositionLocalProvider(
        LocalGraphMain provides graphMain,
        LocalGraphBottom provides graphBottom,
        LocalGraphDashboard provides graphDashboard,
        LocalSignalBus provides signalBus,
    ) {
        MarketsTheme {
            NavDisplay(
                backStack = graphMain,
                onBack = { graphMain.safePop() },
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
}
