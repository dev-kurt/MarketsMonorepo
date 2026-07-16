package com.devkurt.markets.ui.api.state

import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class LoadingCounterTest {

    @Test
    fun `idle by default`() = runTest {
        assertFalse(LoadingCounter().isLoading.first())
    }

    @Test
    fun `loading is true while a block is running`() = runTest {
        val counter = LoadingCounter()
        val gate = CompletableDeferred<Unit>()
        val job = launch { counter.withLoading { gate.await() } }
        runCurrent()
        assertTrue(counter.isLoading.first())
        gate.complete(Unit)
        job.join()
        assertFalse(counter.isLoading.first())
    }

    @Test
    fun `loading stays true until all concurrent blocks finish`() = runTest {
        val counter = LoadingCounter()
        val first = CompletableDeferred<Unit>()
        val second = CompletableDeferred<Unit>()
        val jobs = listOf(
            launch { counter.withLoading { first.await() } },
            launch { counter.withLoading { second.await() } },
        )
        runCurrent()
        assertTrue(counter.isLoading.first())
        first.complete(Unit)
        runCurrent()
        assertTrue(counter.isLoading.first())
        second.complete(Unit)
        jobs.forEach { it.join() }
        assertFalse(counter.isLoading.first())
    }

    @Test
    fun `counter decrements when block throws`() = runTest {
        val counter = LoadingCounter()
        assertFailsWith<IllegalStateException> {
            counter.withLoading { throw IllegalStateException() }
        }
        assertFalse(counter.isLoading.first())
    }

    @Test
    fun `withLoading returns the block result`() = runTest {
        val counter = LoadingCounter()
        assertEquals(42, counter.withLoading { 42 })
    }
}
