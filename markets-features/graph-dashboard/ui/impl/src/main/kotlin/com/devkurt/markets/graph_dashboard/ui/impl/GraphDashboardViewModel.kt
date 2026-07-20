package com.devkurt.markets.graph_dashboard.ui.impl

import androidx.lifecycle.ViewModel
import com.devkurt.markets.graph_dashboard.ui.api.GraphDashboardRoutes

class GraphDashboardViewModel(
    val entryProviders: List<GraphDashboardRoutes>,
) : ViewModel()
