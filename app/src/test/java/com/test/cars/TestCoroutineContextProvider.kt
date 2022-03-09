package com.test.cars

import com.test.cars.data.common.CoroutineContextProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import kotlin.coroutines.CoroutineContext

@ExperimentalCoroutinesApi
class TestCoroutineContextProvider(
    val dispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher(),
    val scope: TestCoroutineScope = TestCoroutineScope(dispatcher)
) : CoroutineContextProvider, TestCoroutineScope by scope {
    override val main: CoroutineContext get() = dispatcher
    override val io: CoroutineContext get() = dispatcher
    override val unconfined: CoroutineContext get() = dispatcher
    override val default: CoroutineContext get() = dispatcher

    fun runBlockingTest(block: suspend TestCoroutineScope.() -> Unit) = this.dispatcher.runBlockingTest(block)
}