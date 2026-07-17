package com.devkurt.markets.graph_dashboard.di

import com.devkurt.markets.graph_bottom.ui.api.GraphBottomRoutes
import com.devkurt.markets.graph_dashboard.ui.api.DashboardPlaceholderRoute
import com.devkurt.markets.graph_dashboard.ui.api.GraphDashboard
import com.devkurt.markets.graph_dashboard.ui.api.GraphDashboardRoute
import com.devkurt.markets.graph_dashboard.ui.api.GraphDashboardRoutes
import com.devkurt.markets.graph_dashboard.ui.impl.DashboardPlaceholderScreen
import com.devkurt.markets.graph_dashboard.ui.impl.GraphDashboardScreen
import com.devkurt.markets.graph_dashboard.ui.impl.GraphDashboardViewModel
import com.devkurt.markets.navigation.api.GraphEntryProvider
import org.koin.core.annotation.Configuration
import org.koin.core.annotation.KoinViewModel
import org.koin.core.annotation.Module
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single

@Module
@Configuration
class GraphDashboardModule {
    @Single(binds = [GraphEntryProvider::class])
    @Named("graphDashboardRoutes")
    fun graphDashboardRoutes(): GraphBottomRoutes = GraphBottomRoutes { scope ->
        scope.entry<GraphDashboardRoute> {
            GraphDashboardScreen()
        }
    }

    @Single(binds = [GraphEntryProvider::class])
    @Named("dashboardPlaceholderRoutes")
    fun dashboardPlaceholderRoutes(): GraphDashboardRoutes = GraphDashboardRoutes { scope ->
        scope.entry<DashboardPlaceholderRoute> {
            DashboardPlaceholderScreen()
        }
    }

    @KoinViewModel
    fun graphDashboardViewModel(
        entryProviders: List<GraphEntryProvider<GraphDashboard>>,
    ): GraphDashboardViewModel = GraphDashboardViewModel(
        entryProviders = entryProviders,
    )
}
