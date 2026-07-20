package com.devkurt.markets.watchlist.ui.impl

import app.cash.turbine.test
import com.devkurt.markets.watchlist.domain.api.model.WatchlistCoin
import com.devkurt.markets.watchlist.domain.api.repository.WatchlistRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

@OptIn(ExperimentalCoroutinesApi::class)
class WatchlistViewModelTest {

    private val dispatcher = StandardTestDispatcher()

    @BeforeTest
    fun setUp() = Dispatchers.setMain(dispatcher)

    @AfterTest
    fun tearDown() = Dispatchers.resetMain()

    private class FakeWatchlistRepository : WatchlistRepository {
        val ids = MutableStateFlow<Set<String>>(emptySet())
        var coinsResult: Result<List<WatchlistCoin>> = Result.success(emptyList())
        val toggledIds = mutableListOf<String>()

        override fun flowWatchlistIds(): Flow<Set<String>> = ids

        override suspend fun toggle(coinId: String) {
            toggledIds += coinId
            ids.update { current ->
                if (coinId in current) current - coinId else current + coinId
            }
        }

        override suspend fun clear() {
            ids.value = emptySet()
        }

        override suspend fun getWatchlistCoins(ids: Set<String>): Result<List<WatchlistCoin>> =
            coinsResult
    }

    private val bitcoin = WatchlistCoin(
        id = "bitcoin",
        symbol = "BTC",
        name = "Bitcoin",
        imageUrl = "https://img/bitcoin",
        price = 1.0,
        changePercent24h = 0.0,
    )

    @Test
    fun `watched coins are loaded into state`() = runTest(dispatcher) {
        val repository = FakeWatchlistRepository().apply {
            ids.value = setOf("bitcoin")
            coinsResult = Result.success(listOf(bitcoin))
        }
        val viewModel = WatchlistViewModel(repository)

        viewModel.state.test {
            advanceUntilIdle()
            val state = expectMostRecentItem()
            assertEquals(listOf(bitcoin), state.coins)
            assertNull(state.error)
        }
    }

    @Test
    fun `load failure surfaces the error`() = runTest(dispatcher) {
        val repository = FakeWatchlistRepository().apply {
            ids.value = setOf("bitcoin")
            coinsResult = Result.failure(IllegalStateException("boom"))
        }
        val viewModel = WatchlistViewModel(repository)

        viewModel.state.test {
            advanceUntilIdle()
            assertEquals("boom", expectMostRecentItem().error)
        }
    }

    @Test
    fun `remove click toggles the coin in the repository`() = runTest(dispatcher) {
        val repository = FakeWatchlistRepository().apply { ids.value = setOf("bitcoin") }
        val viewModel = WatchlistViewModel(repository)

        viewModel.onEvent(WatchlistEvent.RemoveClicked("bitcoin"))
        advanceUntilIdle()

        assertEquals(listOf("bitcoin"), repository.toggledIds)
    }

    @Test
    fun `retry recovers after a failure`() = runTest(dispatcher) {
        val repository = FakeWatchlistRepository().apply {
            ids.value = setOf("bitcoin")
            coinsResult = Result.failure(IllegalStateException("boom"))
        }
        val viewModel = WatchlistViewModel(repository)

        viewModel.state.test {
            advanceUntilIdle()
            assertEquals("boom", expectMostRecentItem().error)

            repository.coinsResult = Result.success(listOf(bitcoin))
            viewModel.onEvent(WatchlistEvent.Retry)
            advanceUntilIdle()

            val recovered = expectMostRecentItem()
            assertEquals(listOf(bitcoin), recovered.coins)
            assertNull(recovered.error)
        }
    }
}
