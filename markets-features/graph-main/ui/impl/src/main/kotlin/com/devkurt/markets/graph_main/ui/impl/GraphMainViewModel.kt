package com.devkurt.markets.graph_main.ui.impl

import androidx.lifecycle.ViewModel
import com.devkurt.markets.navigation.api.GraphMainRoutes
import com.devkurt.markets.navigation.api.GraphMain

class GraphMainViewModel(
    val entryProviders: List<GraphMainRoutes>,
) : ViewModel()
