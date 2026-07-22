package com.devkurt.markets.coin_detail.data.mapper

import com.devkurt.markets.coin_detail.data.remote.dto.CoinDetailDto
import com.devkurt.markets.coin_detail.data.remote.dto.MarketDataDto
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class CoinDetailMapperTest {

    private fun dto(changePercent: Double = 0.0) = CoinDetailDto(
        id = "bitcoin",
        symbol = "btc",
        name = "Bitcoin",
        marketData = MarketDataDto(
            currentPrice = mapOf("usd" to 65_000.5, "eur" to 60_000.0),
            priceChangePercentage24h = changePercent,
        ),
    )

    @Test
    fun `dto fields are mapped onto the domain model`() {
        val coin = dto(changePercent = 2.5).toCoinDetail()

        assertEquals("bitcoin", coin.id)
        assertEquals("BTC", coin.symbol)
        assertEquals(65_000.5, coin.price)
        assertEquals(2.5, coin.changePercent24h)
    }

    @Test
    fun `missing usd price falls back to zero`() {
        val coin = dto().copy(marketData = MarketDataDto()).toCoinDetail()
        assertEquals(0.0, coin.price)
    }

    @Test
    fun `positive change marks the price as up`() {
        assertTrue(dto(changePercent = 0.01).toCoinDetail().isPriceUp)
    }

    @Test
    fun `zero change counts as up`() {
        assertTrue(dto(changePercent = 0.0).toCoinDetail().isPriceUp)
    }

    @Test
    fun `negative change marks the price as down`() {
        assertFalse(dto(changePercent = -0.01).toCoinDetail().isPriceUp)
    }
}
