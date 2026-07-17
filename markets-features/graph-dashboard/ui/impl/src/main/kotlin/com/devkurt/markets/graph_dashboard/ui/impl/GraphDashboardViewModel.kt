package com.devkurt.markets.graph_dashboard.ui.impl

import androidx.lifecycle.ViewModel
import com.devkurt.markets.graph_dashboard.ui.api.GraphDashboard
import com.devkurt.markets.navigation.api.GraphEntryProvider

class GraphDashboardViewModel(
    val entryProviders: List<GraphEntryProvider<GraphDashboard>>,
) : ViewModel()
