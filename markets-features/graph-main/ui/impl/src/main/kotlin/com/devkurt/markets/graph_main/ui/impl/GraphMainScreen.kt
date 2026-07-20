package com.devkurt.markets.graph_main.ui.impl

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.devkurt.markets.coins_list.ui.api.CoinsListRoute
import com.devkurt.markets.dashboard.ui.api.DashboardRoute
import com.devkurt.markets.graph_bottom.ui.api.GraphBottom
import com.devkurt.markets.graph_bottom.ui.api.GraphBottomRoute
import com.devkurt.markets.graph_bottom.ui.api.LocalGraphBottom
import com.devkurt.markets.graph_dashboard.ui.api.GraphDashboard
import com.devkurt.markets.graph_dashboard.ui.api.GraphDashboardRoute
import com.devkurt.markets.graph_dashboard.ui.api.LocalGraphDashboard
import com.devkurt.markets.graph_list.ui.api.GraphList
import com.devkurt.markets.graph_list.ui.api.LocalGraphList
import com.devkurt.markets.navigation.api.GraphMain
import com.devkurt.markets.navigation.api.LocalGraphMain
import com.devkurt.markets.navigation.api.LocalSignalBus
import com.devkurt.markets.navigation.api.SignalBus
import com.devkurt.markets.navigation.api.rememberNavBackStack
import com.devkurt.markets.navigation.api.safePop
import com.devkurt.markets.ui.api.navigation.MkBottomSheetSceneStrategy
import com.devkurt.markets.ui.api.theme.MarketsTheme
import kotlinx.serialization.modules.SerializersModule
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@Composable
fun GraphMainScreen(
    viewModel: GraphMainViewModel = koinViewModel(),
) {
    val serializersModule = koinInject<SerializersModule>()
    val graphMain = rememberNavBackStack<GraphMain>(serializersModule, GraphBottomRoute)
    val graphBottom = rememberNavBackStack<GraphBottom>(serializersModule, GraphDashboardRoute)
    val graphDashboard =
        rememberNavBackStack<GraphDashboard>(serializersModule, DashboardRoute)
    val graphList = rememberNavBackStack<GraphList>(serializersModule, CoinsListRoute)
    val signalBus = koinInject<SignalBus>()
    val bottomSheetStrategy = remember(graphMain) { MkBottomSheetSceneStrategy(graphMain) }

    CompositionLocalProvider(
        LocalGraphMain provides graphMain,
        LocalGraphBottom provides graphBottom,
        LocalGraphDashboard provides graphDashboard,
        LocalGraphList provides graphList,
        LocalSignalBus provides signalBus,
    ) {
        MarketsTheme {
            NavDisplay(
                backStack = graphMain,
                onBack = { graphMain.safePop() },
                sceneStrategies = listOf(bottomSheetStrategy),
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
            viewModel.graphContents.forEach { content -> content.Content() }
        }
    }
}
