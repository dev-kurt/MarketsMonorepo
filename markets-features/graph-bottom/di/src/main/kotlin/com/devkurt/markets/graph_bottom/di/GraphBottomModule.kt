package com.devkurt.markets.graph_bottom.di

import com.devkurt.markets.graph_bottom.ui.api.GraphBottomRoute
import com.devkurt.markets.graph_bottom.ui.api.GraphBottomRoutes
import com.devkurt.markets.graph_bottom.ui.impl.GraphBottomScreen
import com.devkurt.markets.graph_bottom.ui.impl.GraphBottomViewModel
import com.devkurt.markets.navigation.api.GraphMain
import com.devkurt.markets.navigation.api.GraphMainRoutes
import com.devkurt.markets.serialization.api.MkSerializersModule
import kotlinx.serialization.modules.polymorphic
import org.koin.core.annotation.Configuration
import org.koin.core.annotation.KoinViewModel
import org.koin.core.annotation.Module
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single

@Module
@Configuration
class GraphBottomModule {
    @Single
    @Named("graphBottomRoutes")
    fun graphBottomRoutes(): GraphMainRoutes = GraphMainRoutes { scope ->
        scope.entry<GraphBottomRoute> {
            GraphBottomScreen()
        }
    }

    @Single
    @Named("graphBottomRouteSerializers")
    fun graphBottomRouteSerializers(): MkSerializersModule = MkSerializersModule {
        polymorphic(GraphMain::class) {
            subclass(GraphBottomRoute::class, GraphBottomRoute.serializer())
        }
    }

    @KoinViewModel
    fun graphBottomViewModel(
        entryProviders: List<GraphBottomRoutes>,
    ): GraphBottomViewModel = GraphBottomViewModel(
        entryProviders = entryProviders,
    )
}
