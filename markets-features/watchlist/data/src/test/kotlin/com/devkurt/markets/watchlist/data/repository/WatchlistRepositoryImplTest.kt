package com.devkurt.markets.watchlist.data.repository

import androidx.datastore.core.DataStore
import app.cash.turbine.test
import com.devkurt.markets.watchlist.data.local.WatchlistData
import com.devkurt.markets.watchlist.data.remote.api.WatchlistRemoteApi
import com.devkurt.markets.watchlist.domain.api.model.WatchlistCoin
import com.devkurt.markets.serialization.api.MkJson
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class WatchlistRepositoryImplTest {

    private class FakeWatchlistDataStore : DataStore<WatchlistData> {
        private val state = MutableStateFlow(WatchlistData())
        override val data: Flow<WatchlistData> = state
        override suspend fun updateData(
            transform: suspend (t: WatchlistData) -> WatchlistData,
        ): WatchlistData {
            val updated = transform(state.value)
            state.value = updated
            return updated
        }
    }

    private fun repository(
        engine: MockEngine = MockEngine { error("Remote must not be called") },
    ): WatchlistRepositoryImpl = WatchlistRepositoryImpl(
        dataStore = FakeWatchlistDataStore(),
        watchlistRemoteApi = WatchlistRemoteApi(
            HttpClient(engine) {
                install(ContentNegotiation) {
                    json(MkJson.instance)
                }
            },
        ),
    )

    @Test
    fun `toggle adds a coin that is not in the watchlist`() = runTest {
        val repository = repository()
        repository.toggle("bitcoin")
        assertEquals(setOf("bitcoin"), repository.flowWatchlistIds().first())
    }

    @Test
    fun `toggle removes a coin that is already in the watchlist`() = runTest {
        val repository = repository()
        repository.toggle("bitcoin")
        repository.toggle("bitcoin")
        assertEquals(emptySet(), repository.flowWatchlistIds().first())
    }

    @Test
    fun `clear empties the watchlist`() = runTest {
        val repository = repository()
        repository.toggle("bitcoin")
        repository.toggle("ethereum")
        repository.clear()
        assertEquals(emptySet(), repository.flowWatchlistIds().first())
    }

    @Test
    fun `flowWatchlistIds emits every change`() = runTest {
        val repository = repository()
        repository.flowWatchlistIds().test {
            assertEquals(emptySet(), awaitItem())
            repository.toggle("bitcoin")
            assertEquals(setOf("bitcoin"), awaitItem())
            repository.toggle("ethereum")
            assertEquals(setOf("bitcoin", "ethereum"), awaitItem())
        }
    }

    @Test
    fun `getWatchlistCoins with no ids succeeds without calling the remote api`() = runTest {
        val result = repository().getWatchlistCoins(emptySet())
        assertEquals(emptyList(), result.getOrThrow())
    }

    @Test
    fun `getWatchlistCoins maps the remote response to domain models`() = runTest {
        val engine = MockEngine {
            respond(
                content = """[{"id":"bitcoin","symbol":"btc","name":"Bitcoin","image":"https://img/btc.png","current_price":65000.5,"price_change_percentage_24h":null}]""",
                headers = headersOf(HttpHeaders.ContentType, "application/json"),
            )
        }
        val coins = repository(engine = engine).getWatchlistCoins(setOf("bitcoin")).getOrThrow()
        assertEquals(
            listOf(
                WatchlistCoin(
                    id = "bitcoin",
                    symbol = "BTC",
                    name = "Bitcoin",
                    imageUrl = "https://img/btc.png",
                    price = 65000.5,
                    changePercent24h = 0.0,
                ),
            ),
            coins,
        )
    }

    @Test
    fun `null price and missing image survive decoding`() = runTest {
        val engine = MockEngine {
            respond(
                content = """[{"id":"stale","symbol":"stl","name":"Stale","current_price":null}]""",
                headers = headersOf(HttpHeaders.ContentType, "application/json"),
            )
        }
        val coin = repository(engine = engine).getWatchlistCoins(setOf("stale")).getOrThrow().single()
        assertEquals(0.0, coin.price)
        assertEquals("", coin.imageUrl)
    }

    @Test
    fun `getWatchlistCoins returns failure on a server error`() = runTest {
        val engine = MockEngine { respond("boom", HttpStatusCode.InternalServerError) }
        assertTrue(repository(engine = engine).getWatchlistCoins(setOf("bitcoin")).isFailure)
    }
}
