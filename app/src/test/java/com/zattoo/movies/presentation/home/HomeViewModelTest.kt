package com.zattoo.movies.presentation.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.zattoo.movies.core.utils.errorMapper
import com.zattoo.movies.data.remote.Currency
import com.zattoo.movies.data.remote.Image
import com.zattoo.movies.data.remote.Price
import com.zattoo.movies.domain.DomainMovies
import com.zattoo.movies.domain.Failure
import com.zattoo.movies.domain.Success
import com.zattoo.movies.domain.home.GetMoviesUseCaseImpl
import com.zattoo.movies.presentation.utils.CoroutineTestRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class HomeViewModelTest {
    private val getMoviesUseCaseImpl: GetMoviesUseCaseImpl = mock()
    private val domainMoviesObserver: Observer<UIState> = mock()
    private lateinit var systemUnderTest: HomeViewModel

    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        systemUnderTest = HomeViewModel(getMoviesUseCaseImpl)
        systemUnderTest.viewState.observeForever(domainMoviesObserver)
    }

    @Test
    fun `fetchMovies should invoke getMoviesUseCaseImpl only once`() {
        runBlocking {
            val list = listOf(movies())
            val flowTypeList = flow { emit(Success(list)) }.flowOn(Dispatchers.IO)

            whenever(getMoviesUseCaseImpl.execute()).thenReturn(flowTypeList)

            systemUnderTest.fetchMovies()

            verify(getMoviesUseCaseImpl, times(1)).execute()
        }
    }

    @Test
    fun `fetchMovies should get list of domain movies when getMoviesUseCase is executed`() {
        runBlocking {
            val list = listOf(movies())
            val flowTypeList = flow { emit(Success(list)) }.flowOn(Dispatchers.IO)

            whenever(getMoviesUseCaseImpl.execute()).thenReturn(flowTypeList)

            systemUnderTest.fetchMovies()

            verify(domainMoviesObserver).onChanged(UIState.Loading)
            verify(domainMoviesObserver).onChanged(UIState.Ready(list))
        }
    }

    @Test
    fun `fetchMovies should return error when getMoviesUseCase is executed`() {
        runBlocking {
            val flowType = flow { emit(Failure(500)) }.flowOn(Dispatchers.IO)

            whenever(getMoviesUseCaseImpl.execute()).thenReturn(flowType)

            systemUnderTest.fetchMovies()

            verify(domainMoviesObserver).onChanged(UIState.Loading)
            verify(domainMoviesObserver).onChanged(UIState.Error(errorMapper(500)))
        }
    }

    private fun movies(): DomainMovies {
        return DomainMovies("Hello","There", Price(12F, Currency("euro")), Image("url"),true, 1)
    }
}