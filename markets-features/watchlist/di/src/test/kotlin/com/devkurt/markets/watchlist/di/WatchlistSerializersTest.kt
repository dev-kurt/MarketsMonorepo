package com.devkurt.markets.watchlist.di

import com.devkurt.markets.navigation.api.GraphMain
import com.devkurt.markets.watchlist.ui.api.WatchlistRoute
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.modules.SerializersModule
import kotlin.test.Test
import kotlin.test.assertNotNull

@OptIn(ExperimentalSerializationApi::class)
class WatchlistSerializersTest {

    private val serializersModule = SerializersModule {
        with(WatchlistModule().watchlistRouteSerializers()) { register() }
    }

    @Test
    fun `WatchlistRoute is registered under GraphMain`() {
        assertNotNull(serializersModule.getPolymorphic(GraphMain::class, WatchlistRoute))
    }
}
