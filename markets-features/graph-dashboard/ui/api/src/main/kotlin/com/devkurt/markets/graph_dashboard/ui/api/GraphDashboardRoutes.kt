package com.devkurt.markets.graph_dashboard.ui.api

import androidx.navigation3.runtime.EntryProviderScope
import com.devkurt.markets.navigation.api.GraphEntryProvider

fun interface GraphDashboardRoutes : GraphEntryProvider<GraphDashboard> {
    override fun EntryProviderScope<GraphDashboard>.install() {
        invoke(this)
    }

    fun invoke(scope: EntryProviderScope<GraphDashboard>)
}
