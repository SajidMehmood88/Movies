package com.zattoo.movies.data.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.zattoo.movies.data.remote.MovieListEntity
import com.zattoo.movies.data.remote.MovieService
import com.zattoo.movies.domain.*
import com.zattoo.movies.presentation.utils.CoroutineTestRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response


@ExperimentalCoroutinesApi
class MovieDetailsRepositoryImplTest {

    private val service: MovieService = mock()
    private lateinit var systemUnderTest: MovieDetailsRepositoryImpl

    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before()
    fun setup() {
        systemUnderTest =
            MovieDetailsRepositoryImpl(service, coroutineTestRule.testDispatcherProvider.io())
    }

    @Test
    fun `getMovieDetail should return a Success response with a list of movies`() =
        coroutineTestRule.testDispatcher.runBlockingTest {
            val mockObject =
                MovieListEntity(listOf(MovieListEntity.MovieData(1, "Hello World", "Hello")))
            val movieResponse = Response.success(mockObject)

            whenever(service.fetchMovieList()).thenReturn(movieResponse)

            val response = systemUnderTest.getMovieDetail()

            assertThat(response.first()).isEqualTo(Success(movieResponse.body()!!.movie_data))
        }

    @Test
    fun `getMovieDetail should return Failure with http error`() =
        coroutineTestRule.testDispatcher.runBlockingTest {
            val exception = Response.error<MovieListEntity>(
                400,
                "Test Server Error".toResponseBody("text/plain".toMediaTypeOrNull())
            )

            whenever(service.fetchMovieList()).thenReturn(exception)

            val response =
                systemUnderTest.getMovieDetail().first().mapOnSuccess { item -> item.hashCode() }

            assertThat(response).isEqualTo(Failure(exception.code()))
        }

    @Test
    fun `getMovieDetail should return Failure when list is empty`() {
        coroutineTestRule.testDispatcher.runBlockingTest {
            val mockObject = MovieListEntity(emptyList())
            val movieResponse = Response.success(mockObject)

            whenever(service.fetchMovieList()).thenReturn(movieResponse)

            val response = systemUnderTest.getMovieDetail()

            assertThat(response.first()).isEqualTo(movieResponse.body()?.let { Failure(EMPTY_LIST) })
        }
    }

    companion object {
        const val EMPTY_LIST = 204
    }
}