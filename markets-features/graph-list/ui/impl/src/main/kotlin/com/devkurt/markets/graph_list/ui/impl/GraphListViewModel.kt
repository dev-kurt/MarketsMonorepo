package com.devkurt.markets.graph_list.ui.impl

import androidx.lifecycle.ViewModel
import com.devkurt.markets.graph_list.ui.api.GraphListRoutes

class GraphListViewModel(
    val entryProviders: List<GraphListRoutes>,
) : ViewModel()
