package com.zattoo.movies.presentation.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description

/**
 * A test rule to allow testing coroutines that use the main dispatcher
 */
@ExperimentalCoroutinesApi
class CoroutineTestRule(val testDispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()) :
	TestWatcher(),
	TestCoroutineScope by TestCoroutineScope(testDispatcher) {

	val testDispatcherProvider = object : DispatcherProvider {
		override fun default(): CoroutineDispatcher = testDispatcher
		override fun io(): CoroutineDispatcher = testDispatcher
		override fun main(): CoroutineDispatcher = testDispatcher
		override fun unconfined(): CoroutineDispatcher = testDispatcher
	}

	override fun starting(description: Description?) {
		super.starting(description)
		Dispatchers.setMain(testDispatcher)
	}

	override fun finished(description: Description?) {
		super.finished(description)
		Dispatchers.resetMain()
		testDispatcher.cleanupTestCoroutines()
	}
}