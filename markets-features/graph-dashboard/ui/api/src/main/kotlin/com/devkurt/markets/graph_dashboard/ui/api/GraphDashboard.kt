package com.devkurt.markets.graph_dashboard.ui.api

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidedValue
import androidx.compose.runtime.compositionLocalOf
import androidx.navigation3.runtime.NavBackStack
import com.devkurt.markets.navigation.api.GraphMarkets

interface GraphDashboard : GraphMarkets

object LocalGraphDashboard {
    private val local = compositionLocalOf<NavBackStack<GraphDashboard>?> { null }

    val current: NavBackStack<GraphDashboard>
        @Composable
        get() = local.current ?: error("No GraphDashboard navigation provided")

    val currentOrNull: NavBackStack<GraphDashboard>?
        @Composable
        get() = local.current

    infix fun provides(
        navBackStack: NavBackStack<GraphDashboard>,
    ): ProvidedValue<NavBackStack<GraphDashboard>?> = local.provides(navBackStack)
}
