package com.devkurt.markets.graph_main.ui.impl

import androidx.lifecycle.ViewModel
import com.devkurt.markets.navigation.api.GraphMainContent
import com.devkurt.markets.navigation.api.GraphMainRoutes

class GraphMainViewModel(
    val entryProviders: List<GraphMainRoutes>,
    val graphContents: List<GraphMainContent>,
) : ViewModel()
