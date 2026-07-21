package com.devkurt.markets.serialization.api

import kotlinx.serialization.Serializable
import kotlin.test.Test
import kotlin.test.assertEquals

class MkJsonTest {

    @Serializable
    private data class Fixture(
        val id: String,
        val rank: Int? = null,
        val price: Double = 0.0,
        val kind: Kind = Kind.COIN,
    )

    @Serializable
    private enum class Kind { COIN, TOKEN }

    @Test
    fun `unknown keys are ignored`() {
        val decoded = MkJson.instance.decodeFromString<Fixture>(
            """{"id":"btc","surprise_field":123}""",
        )
        assertEquals("btc", decoded.id)
    }

    @Test
    fun `missing nullable field decodes to null`() {
        val decoded = MkJson.instance.decodeFromString<Fixture>("""{"id":"btc"}""")
        assertEquals(null, decoded.rank)
    }

    @Test
    fun `explicit null is coerced into the default value`() {
        val decoded = MkJson.instance.decodeFromString<Fixture>(
            """{"id":"btc","price":null}""",
        )
        assertEquals(0.0, decoded.price)
    }

    @Test
    fun `unknown enum value is coerced into the default`() {
        val decoded = MkJson.instance.decodeFromString<Fixture>(
            """{"id":"btc","kind":"NFT"}""",
        )
        assertEquals(Kind.COIN, decoded.kind)
    }

    @Test
    fun `enum decoding is case insensitive`() {
        val decoded = MkJson.instance.decodeFromString<Fixture>(
            """{"id":"btc","kind":"token"}""",
        )
        assertEquals(Kind.TOKEN, decoded.kind)
    }
}
