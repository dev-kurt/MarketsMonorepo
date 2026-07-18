package com.devkurt.markets.graph_main.di

import com.devkurt.markets.graph_bottom.di.GraphBottomModule
import com.devkurt.markets.graph_bottom.ui.api.GraphBottom
import com.devkurt.markets.graph_bottom.ui.api.GraphBottomRoute
import com.devkurt.markets.graph_dashboard.di.GraphDashboardModule
import com.devkurt.markets.graph_dashboard.ui.api.DashboardPlaceholderRoute
import com.devkurt.markets.graph_dashboard.ui.api.GraphDashboard
import com.devkurt.markets.graph_dashboard.ui.api.GraphDashboardRoute
import com.devkurt.markets.navigation.api.GraphMain
import com.devkurt.markets.navigation.di.NavigationModule
import kotlin.test.Test
import kotlin.test.assertNotNull

class RouteSerializersTest {

    private val module = NavigationModule().routeSerializersModule(
        listOf(
            GraphBottomModule().graphBottomRouteSerializers(),
            GraphDashboardModule().graphDashboardRouteSerializers(),
        ),
    )

    @Test
    fun `GraphBottomRoute is registered under GraphMain`() {
        assertNotNull(module.getPolymorphic(GraphMain::class, GraphBottomRoute))
    }

    @Test
    fun `GraphDashboardRoute is registered under GraphBottom`() {
        assertNotNull(module.getPolymorphic(GraphBottom::class, GraphDashboardRoute))
    }

    @Test
    fun `DashboardPlaceholderRoute is registered under GraphDashboard`() {
        assertNotNull(module.getPolymorphic(GraphDashboard::class, DashboardPlaceholderRoute))
    }
}
