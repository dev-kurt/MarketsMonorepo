package com.devkurt.markets.search.data.repository

import com.devkurt.markets.search.data.remote.api.SearchRemoteApi
import com.devkurt.markets.search.domain.api.model.SearchCoin
import com.devkurt.markets.serialization.api.MkJson
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class SearchRepositoryImplTest {

    private fun repository(
        engine: MockEngine = MockEngine { error("Remote must not be called") },
    ): SearchRepositoryImpl = SearchRepositoryImpl(
        searchRemoteApi = SearchRemoteApi(
            HttpClient(engine) {
                install(ContentNegotiation) {
                    json(MkJson.instance)
                }
            },
        ),
    )

    @Test
    fun `blank query succeeds without calling the remote api`() = runTest {
        val result = repository().search("   ")
        assertEquals(emptyList(), result.getOrThrow())
    }

    @Test
    fun `response is mapped to domain models`() = runTest {
        val engine = MockEngine {
            respond(
                content = """{"coins":[{"id":"bitcoin","name":"Bitcoin","symbol":"BTC","market_cap_rank":1,"large":"https://img/btc.png","thumb":"https://img/btc-thumb.png"}],"exchanges":[]}""",
                headers = headersOf(HttpHeaders.ContentType, "application/json"),
            )
        }
        val coins = repository(engine = engine).search("bitcoin").getOrThrow()
        assertEquals(
            listOf(
                SearchCoin(
                    id = "bitcoin",
                    name = "Bitcoin",
                    symbol = "BTC",
                    marketCapRank = 1,
                    imageUrl = "https://img/btc.png",
                ),
            ),
            coins,
        )
    }

    @Test
    fun `null market cap rank is preserved`() = runTest {
        val engine = MockEngine {
            respond(
                content = """{"coins":[{"id":"tiny","name":"Tiny","symbol":"TNY","market_cap_rank":null,"large":"https://img/t.png"}]}""",
                headers = headersOf(HttpHeaders.ContentType, "application/json"),
            )
        }
        val coins = repository(engine = engine).search("tiny").getOrThrow()
        assertEquals(null, coins.single().marketCapRank)
    }

    @Test
    fun `missing image url survives decoding`() = runTest {
        val engine = MockEngine {
            respond(
                content = """{"coins":[{"id":"tiny","name":"Tiny","symbol":"TNY"}]}""",
                headers = headersOf(HttpHeaders.ContentType, "application/json"),
            )
        }
        val coin = repository(engine = engine).search("tiny").getOrThrow().single()
        assertEquals("", coin.imageUrl)
    }

    @Test
    fun `server error returns failure`() = runTest {
        val engine = MockEngine { respond("boom", HttpStatusCode.TooManyRequests) }
        assertTrue(repository(engine = engine).search("bitcoin").isFailure)
    }
}
