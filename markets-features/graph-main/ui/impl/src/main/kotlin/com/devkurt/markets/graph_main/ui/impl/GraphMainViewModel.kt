package com.devkurt.markets.graph_main.ui.impl

import androidx.lifecycle.ViewModel
import com.devkurt.markets.navigation.api.GraphEntryProvider
import com.devkurt.markets.navigation.api.GraphMain

class GraphMainViewModel(
    val entryProviders: List<GraphEntryProvider<GraphMain>>,
) : ViewModel()
