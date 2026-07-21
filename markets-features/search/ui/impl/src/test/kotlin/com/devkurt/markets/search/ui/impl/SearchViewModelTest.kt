package com.devkurt.markets.search.ui.impl

import app.cash.turbine.test
import com.devkurt.markets.search.domain.api.model.SearchCoin
import com.devkurt.markets.search.domain.api.repository.SearchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
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
class SearchViewModelTest {

    private val dispatcher = StandardTestDispatcher()

    @BeforeTest
    fun setUp() = Dispatchers.setMain(dispatcher)

    @AfterTest
    fun tearDown() = Dispatchers.resetMain()

    private class FakeSearchRepository : SearchRepository {
        var result: Result<List<SearchCoin>> = Result.success(emptyList())
        val queries = mutableListOf<String>()

        override suspend fun search(query: String): Result<List<SearchCoin>> {
            queries += query
            return result
        }
    }

    private val bitcoin = SearchCoin(
        id = "bitcoin",
        name = "Bitcoin",
        symbol = "BTC",
        marketCapRank = 1,
        imageUrl = "https://img/btc.png",
    )

    @Test
    fun `typing is debounced into a single search`() = runTest(dispatcher) {
        val repository = FakeSearchRepository().apply {
            result = Result.success(listOf(bitcoin))
        }
        val viewModel = SearchViewModel(repository)

        viewModel.state.test {
            viewModel.onEvent(SearchEvent.QueryChanged("b"))
            advanceTimeBy(100)
            viewModel.onEvent(SearchEvent.QueryChanged("bi"))
            advanceTimeBy(100)
            viewModel.onEvent(SearchEvent.QueryChanged("bitcoin"))
            advanceUntilIdle()

            assertEquals(listOf("bitcoin"), repository.queries)
            assertEquals(listOf(bitcoin), expectMostRecentItem().results)
        }
    }

    @Test
    fun `failure surfaces the error and retry recovers`() = runTest(dispatcher) {
        val repository = FakeSearchRepository().apply {
            result = Result.failure(IllegalStateException("boom"))
        }
        val viewModel = SearchViewModel(repository)

        viewModel.state.test {
            viewModel.onEvent(SearchEvent.QueryChanged("bitcoin"))
            advanceUntilIdle()
            assertEquals("boom", expectMostRecentItem().error)

            repository.result = Result.success(listOf(bitcoin))
            viewModel.onEvent(SearchEvent.Retry)
            advanceUntilIdle()

            val recovered = expectMostRecentItem()
            assertEquals(listOf(bitcoin), recovered.results)
            assertNull(recovered.error)
        }
    }
}
