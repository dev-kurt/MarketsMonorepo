package com.devkurt.markets.graph_bottom.ui.impl

import androidx.lifecycle.ViewModel
import com.devkurt.markets.graph_bottom.ui.api.GraphBottom
import com.devkurt.markets.navigation.api.GraphEntryProvider

class GraphBottomViewModel(
    val entryProviders: List<GraphEntryProvider<GraphBottom>>,
) : ViewModel()
