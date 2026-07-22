package com.devkurt.markets.watchlist.data.mapper

import com.devkurt.markets.watchlist.data.remote.dto.WatchlistCoinDto
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class WatchlistMapperTest {

    private fun dto(changePercent: Double = 0.0) = WatchlistCoinDto(
        id = "bitcoin",
        symbol = "btc",
        name = "Bitcoin",
        image = "https://img/btc.png",
        currentPrice = 65_000.5,
        priceChangePercentage24h = changePercent,
    )

    @Test
    fun `dto fields are mapped onto the domain model`() {
        val coin = dto(changePercent = 2.5).toWatchlistCoin()

        assertEquals("bitcoin", coin.id)
        assertEquals("BTC", coin.symbol)
        assertEquals("Bitcoin", coin.name)
        assertEquals("https://img/btc.png", coin.imageUrl)
        assertEquals(65_000.5, coin.price)
        assertEquals(2.5, coin.changePercent24h)
    }

    @Test
    fun `positive change marks the price as up`() {
        assertTrue(dto(changePercent = 0.01).toWatchlistCoin().isPriceUp)
    }

    @Test
    fun `zero change counts as up`() {
        assertTrue(dto(changePercent = 0.0).toWatchlistCoin().isPriceUp)
    }

    @Test
    fun `negative change marks the price as down`() {
        assertFalse(dto(changePercent = -0.01).toWatchlistCoin().isPriceUp)
    }
}
