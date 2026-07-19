package com.devkurt.markets.graph_list.di

import com.devkurt.markets.graph_bottom.ui.api.GraphBottom
import com.devkurt.markets.graph_bottom.ui.api.GraphBottomRoutes
import com.devkurt.markets.graph_list.ui.api.GraphList
import com.devkurt.markets.graph_list.ui.api.GraphListRoute
import com.devkurt.markets.graph_list.ui.impl.GraphListScreen
import com.devkurt.markets.graph_list.ui.impl.GraphListViewModel
import com.devkurt.markets.navigation.api.GraphEntryProvider
import com.devkurt.markets.serialization.api.MkSerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import org.koin.core.annotation.Configuration
import org.koin.core.annotation.KoinViewModel
import org.koin.core.annotation.Module
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single

@Module
@Configuration
class GraphListModule {
    @Single(binds = [GraphEntryProvider::class])
    @Named("graphListRoutes")
    fun graphListRoutes(): GraphBottomRoutes = GraphBottomRoutes { scope ->
        scope.entry<GraphListRoute> {
            GraphListScreen()
        }
    }

    @Single
    @Named("graphListRouteSerializers")
    fun graphListRouteSerializers(): MkSerializersModule = MkSerializersModule {
        polymorphic(GraphBottom::class) {
            subclass(GraphListRoute::class, GraphListRoute.serializer())
        }
    }

    @KoinViewModel
    fun graphListViewModel(
        entryProviders: List<GraphEntryProvider<GraphList>>,
    ): GraphListViewModel = GraphListViewModel(
        entryProviders = entryProviders,
    )
}
