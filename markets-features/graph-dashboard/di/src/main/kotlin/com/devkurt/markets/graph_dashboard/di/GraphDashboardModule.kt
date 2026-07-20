package com.devkurt.markets.graph_dashboard.di

import com.devkurt.markets.graph_bottom.ui.api.GraphBottom
import com.devkurt.markets.graph_bottom.ui.api.GraphBottomRoutes
import com.devkurt.markets.graph_dashboard.ui.api.DashboardPlaceholderRoute
import com.devkurt.markets.graph_dashboard.ui.api.GraphDashboard
import com.devkurt.markets.graph_dashboard.ui.api.GraphDashboardRoute
import com.devkurt.markets.graph_dashboard.ui.api.GraphDashboardRoutes
import com.devkurt.markets.graph_dashboard.ui.impl.DashboardPlaceholderScreen
import com.devkurt.markets.graph_dashboard.ui.impl.GraphDashboardScreen
import com.devkurt.markets.graph_dashboard.ui.impl.GraphDashboardViewModel
import com.devkurt.markets.serialization.api.MkSerializersModule
import kotlinx.serialization.modules.polymorphic
import org.koin.core.annotation.Configuration
import org.koin.core.annotation.KoinViewModel
import org.koin.core.annotation.Module
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single

@Module
@Configuration
class GraphDashboardModule {
    @Single
    @Named("graphDashboardRoutes")
    fun graphDashboardRoutes(): GraphBottomRoutes = GraphBottomRoutes { scope ->
        scope.entry<GraphDashboardRoute> {
            GraphDashboardScreen()
        }
    }

    @Single
    @Named("dashboardPlaceholderRoutes")
    fun dashboardPlaceholderRoutes(): GraphDashboardRoutes = GraphDashboardRoutes { scope ->
        scope.entry<DashboardPlaceholderRoute> {
            DashboardPlaceholderScreen()
        }
    }

    @Single
    @Named("graphDashboardRouteSerializers")
    fun graphDashboardRouteSerializers(): MkSerializersModule = MkSerializersModule {
        polymorphic(GraphBottom::class) {
            subclass(GraphDashboardRoute::class, GraphDashboardRoute.serializer())
        }
        polymorphic(GraphDashboard::class) {
            subclass(DashboardPlaceholderRoute::class, DashboardPlaceholderRoute.serializer())
        }
    }

    @KoinViewModel
    fun graphDashboardViewModel(
        entryProviders: List<GraphDashboardRoutes>,
    ): GraphDashboardViewModel = GraphDashboardViewModel(
        entryProviders = entryProviders,
    )
}
