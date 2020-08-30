package com.tsquaredapps.employeedirectory.common

import androidx.lifecycle.Observer
import io.mockk.clearMocks
import io.mockk.mockk
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.RegisterExtension

/**
 * Base class for testing ViewModels that use Coroutines
 * and use a sealed class and LiveData to communicate
 * with its corresponding view
 */
@ExtendWith(InstantExecutorExtension::class)
abstract class BaseCoroutineViewModelTest<T> {
    var stateList = mutableListOf<T>()
    val stateObserver = mockk<Observer<T>>(relaxed = true)

    @ExperimentalCoroutinesApi
    private val testDispatcher = TestCoroutineDispatcher()

    @ExperimentalCoroutinesApi
    val testDispatcherProvider = object : DispatcherProvider {
        override fun default(): CoroutineDispatcher = testDispatcher
        override fun io(): CoroutineDispatcher = testDispatcher
        override fun main(): CoroutineDispatcher = testDispatcher
        override fun unconfined(): CoroutineDispatcher = testDispatcher
    }

    @ExperimentalCoroutinesApi
    @JvmField
    @RegisterExtension
    val coroutinesTestExtension = CoroutineTestExtension(testDispatcher)

    @BeforeEach
    fun reset() {
        clearMocks(stateObserver)
        stateList.clear()
    }
}