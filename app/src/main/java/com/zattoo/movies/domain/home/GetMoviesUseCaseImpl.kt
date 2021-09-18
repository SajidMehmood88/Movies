package com.zattoo.movies.domain.home

import com.zattoo.movies.data.home.mapper.HomeDataMapper
import com.zattoo.movies.domain.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class GetMoviesUseCaseImpl(
    private val mapper: HomeDataMapper,
    private val movieDetailRepository: MovieDetailsRepository,
    private val movieOfferRepository: MovieOffersRepository
) : GetMoviesUseCase {
    override suspend fun execute(): Flow<Results<List<DomainMovies>>> {
        return movieDetailRepository.getMovieDetail()
            .combine(movieOfferRepository.getMovieOffer()) { movies, offers ->
                if (offers is Success && movies is Success) {
                    Success(
                        mapper.mapServerResponseToDomainMovies(
                            movies.data,
                            offers.data.offers,
                            offers.data.image_base
                        )
                    )
                } else {
                    var error = 0
                    if (movies is Failure || offers is Failure) {
                        movies.onError { item ->  error = item }
                        offers.onError { item -> error = item }
                    }
                    Failure(error)
                }
            }
    }
}