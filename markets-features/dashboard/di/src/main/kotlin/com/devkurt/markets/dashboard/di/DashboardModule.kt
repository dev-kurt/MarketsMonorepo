package com.devkurt.markets.dashboard.di

import com.devkurt.markets.dashboard.ui.api.DashboardRoute
import com.devkurt.markets.dashboard.ui.impl.DashboardViewModel
import com.devkurt.markets.dashboard.ui.impl.DashboardWrapper
import com.devkurt.markets.graph_dashboard.ui.api.GraphDashboard
import com.devkurt.markets.graph_dashboard.ui.api.GraphDashboardRoutes
import com.devkurt.markets.serialization.api.MkSerializersModule
import com.devkurt.markets.watchlist.domain.api.repository.WatchlistRepository
import kotlinx.serialization.modules.polymorphic
import org.koin.core.annotation.Configuration
import org.koin.core.annotation.KoinViewModel
import org.koin.core.annotation.Module
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single

@Module
@Configuration
class DashboardModule {
    @Single
    @Named("dashboardRoutes")
    fun dashboardRoutes(): GraphDashboardRoutes = GraphDashboardRoutes { scope ->
        scope.entry<DashboardRoute> {
            DashboardWrapper()
        }
    }

    @Single
    @Named("dashboardRouteSerializers")
    fun dashboardRouteSerializers(): MkSerializersModule = MkSerializersModule {
        polymorphic(GraphDashboard::class) {
            subclass(DashboardRoute::class, DashboardRoute.serializer())
        }
    }

    @KoinViewModel
    fun dashboardViewModel(
        watchlistRepository: WatchlistRepository,
    ): DashboardViewModel = DashboardViewModel(
        watchlistRepository = watchlistRepository,
    )
}
