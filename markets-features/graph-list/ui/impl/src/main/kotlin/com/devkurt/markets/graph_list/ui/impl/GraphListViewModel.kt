package com.devkurt.markets.graph_list.ui.impl

import androidx.lifecycle.ViewModel
import com.devkurt.markets.graph_list.ui.api.GraphList
import com.devkurt.markets.navigation.api.GraphEntryProvider

class GraphListViewModel(
    val entryProviders: List<GraphEntryProvider<GraphList>>,
) : ViewModel()
