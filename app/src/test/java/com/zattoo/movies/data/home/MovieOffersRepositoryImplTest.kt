package com.zattoo.movies.data.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.zattoo.movies.data.remote.MovieListOffers
import com.zattoo.movies.data.remote.MovieService
import com.zattoo.movies.domain.Failure
import com.zattoo.movies.domain.Success
import com.zattoo.movies.domain.mapOnSuccess
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
class MovieOffersRepositoryImplTest {

    private val service: MovieService = mock()
    private lateinit var systemUnderTest: MovieOffersRepositoryImpl

    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before()
    fun setup() {
        systemUnderTest =
            MovieOffersRepositoryImpl(service, coroutineTestRule.testDispatcherProvider.io())
    }

    @Test
    fun `getMovieOffer should return a Success response with a list of offers`() =
        coroutineTestRule.testDispatcher.runBlockingTest {
            val mockObject = MovieListOffers(
                "url", listOf(
                    MovieListOffers.MovieOffer(
                        true, "/234", 1, "12"
                    )
                )
            )
            val offersResponse = Response.success(mockObject)

            whenever(service.fetchMovieListOffers()).thenReturn(offersResponse)

            val response = systemUnderTest.getMovieOffer().first()

            assertThat(response.mapOnSuccess { item -> item.offers }).isEqualTo(
                Success(
                    offersResponse.body()!!.offers
                )
            )
        }

    @Test
    fun `getMovieOffer should return Failure with http error `() {
        coroutineTestRule.testDispatcher.runBlockingTest {
            val exception = Response.error<MovieListOffers>(
                400,
                "Test Server Error".toResponseBody("text/plain".toMediaTypeOrNull())
            )

            whenever(service.fetchMovieListOffers()).thenReturn(exception)

            val response =
                systemUnderTest.getMovieOffer().first().mapOnSuccess { item -> item.hashCode() }

            assertThat(response).isEqualTo(Failure(exception.code()))
        }
    }
}
