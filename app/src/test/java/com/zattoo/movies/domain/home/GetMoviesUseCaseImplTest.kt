package com.zattoo.movies.domain.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.zattoo.movies.data.home.mapper.HomeDataMapper
import com.zattoo.movies.data.remote.*
import com.zattoo.movies.domain.*
import com.zattoo.movies.presentation.utils.CoroutineTestRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class GetMoviesUseCaseImplTest {

    private val mapper: HomeDataMapper = mock()
    private val movieDetailRepository: MovieDetailsRepository = mock()
    private val movieOfferRepository: MovieOffersRepository = mock()
    private lateinit var systemUnderTest: GetMoviesUseCaseImpl

    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before()
    fun setup() {
        systemUnderTest =
            GetMoviesUseCaseImpl(mapper, movieDetailRepository, movieOfferRepository)
    }

    @Test
    fun `execute should return DomainMovies with success`() {
        runBlocking {
            val movieDetailsResponse = Success(mockDetailsObject())
            val offersResponse = Success(mockOffersObject())
            val mapperResponse = domainMovieList(offersResponse, movieDetailsResponse)

            whenever(movieDetailRepository.getMovieDetail()).thenReturn(flowOf(movieDetailsResponse))
            whenever(movieOfferRepository.getMovieOffer()).thenReturn(flowOf(offersResponse))
            whenever(
                mapper.mapServerResponseToDomainMovies(
                    movieDetailsResponse.data, offersResponse.data.offers,
                    offersResponse.data.image_base
                )
            ).thenReturn(mapperResponse)

            val response = systemUnderTest.execute()

            assertThat(response.first()).isEqualTo(Success(mapperResponse))
        }
    }

    @Test
    fun `execute should return Failure when movie response has failed`() {
        runBlocking {
            val movieResponse = Failure(3000)
            val offersResponse = Success(mockOffersObject())

            whenever(movieDetailRepository.getMovieDetail()).thenReturn(flowOf(movieResponse))
            whenever(movieOfferRepository.getMovieOffer()).thenReturn(flowOf(offersResponse))

            val response = systemUnderTest.execute()

            assertThat(response.first()).isEqualTo(movieResponse)
        }
    }

    @Test
    fun `execute should return Failure when offers response has failed`() {
        runBlocking {
            val movieResponse = Success(mockDetailsObject())
            val offersResponse = Failure(4000)

            whenever(movieDetailRepository.getMovieDetail()).thenReturn(flowOf(movieResponse))
            whenever(movieOfferRepository.getMovieOffer()).thenReturn(flowOf(offersResponse))

            val response = systemUnderTest.execute()

            assertThat(response.first()).isEqualTo(offersResponse)
        }
    }

    @Test
    fun `execute should return Failure when both offers and movies have failed`() {
        runBlocking {
            val movieResponse = Failure(3000)
            val offersResponse = Failure(30)

            whenever(movieDetailRepository.getMovieDetail()).thenReturn(flowOf(movieResponse))
            whenever(movieOfferRepository.getMovieOffer()).thenReturn(flowOf(offersResponse))

            val response = systemUnderTest.execute()

            assertThat(response.first()).isEqualTo(offersResponse)
        }
    }

    private fun domainMovieList(
        offersResponse: Success<MovieListOffers>,
        movieDetailsResponse: Success<List<MovieListEntity.MovieData>>
    ): List<DomainMovies> {
        val movieOfferPrice = offersResponse.data.offers[0].price
        val currency = Currency(movieOfferPrice.last().toString())
        val price = movieOfferPrice.substring(0 until movieOfferPrice.length - 1).toFloat()

        val dataRes = movieDetailsResponse.data[0]

        val image = Image(offersResponse.data.image_base + offersResponse.data.offers[0].image)
        return listOf(
            DomainMovies(
                dataRes.title,
                dataRes.sub_title,
                Price(price, currency),
                image,
                offersResponse.data.offers[0].available,
                dataRes.movie_id
            )
        )
    }

    private fun mockDetailsObject(): List<MovieListEntity.MovieData> {
        val mockDetailsObject =
            MovieListEntity(listOf(MovieListEntity.MovieData(1, "Hello World", "Hello")))
        return mockDetailsObject.movie_data
    }

    private fun mockOffersObject(): MovieListOffers {
        return MovieListOffers(
            "url", listOf(
                MovieListOffers.MovieOffer(
                    true, "/234", 1, "12"
                )
            )
        )
    }
}