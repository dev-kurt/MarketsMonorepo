package com.devkurt.markets.coins_list.data.mapper

import com.devkurt.markets.coins_list.data.remote.dto.CoinMarketDto
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class CoinsListMapperTest {

    private fun dto(changePercent: Double = 0.0) = CoinMarketDto(
        id = "bitcoin",
        symbol = "btc",
        name = "Bitcoin",
        image = "https://img/btc.png",
        currentPrice = 65_000.5,
        priceChangePercentage24h = changePercent,
        marketCapRank = 1,
    )

    @Test
    fun `dto fields are mapped onto the domain model`() {
        val coin = dto(changePercent = 2.5).toCoin()

        assertEquals("bitcoin", coin.id)
        assertEquals("BTC", coin.symbol)
        assertEquals("Bitcoin", coin.name)
        assertEquals("https://img/btc.png", coin.imageUrl)
        assertEquals(65_000.5, coin.price)
        assertEquals(2.5, coin.changePercent24h)
        assertEquals(1, coin.marketCapRank)
    }

    @Test
    fun `positive change marks the price as up`() {
        assertTrue(dto(changePercent = 0.01).toCoin().isPriceUp)
    }

    @Test
    fun `zero change counts as up`() {
        assertTrue(dto(changePercent = 0.0).toCoin().isPriceUp)
    }

    @Test
    fun `negative change marks the price as down`() {
        assertFalse(dto(changePercent = -0.01).toCoin().isPriceUp)
    }
}
