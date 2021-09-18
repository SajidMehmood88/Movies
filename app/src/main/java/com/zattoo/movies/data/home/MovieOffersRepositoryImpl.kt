package com.zattoo.movies.data.home

import com.zattoo.movies.data.remote.MovieListOffers
import com.zattoo.movies.data.remote.MovieService
import com.zattoo.movies.domain.Failure
import com.zattoo.movies.domain.MovieOffersRepository
import com.zattoo.movies.domain.Results
import com.zattoo.movies.domain.Success
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
import java.io.IOException

class MovieOffersRepositoryImpl(
    private val service: MovieService,
    private val backgroundDispatcher: CoroutineDispatcher
) : MovieOffersRepository {
    override fun getMovieOffer(): Flow<Results<MovieListOffers>> = flow {
        try {
            val allOffers = service.fetchMovieListOffers();
            if (allOffers.isSuccessful) {
                val offers = allOffers.body()
                if (offers != null) {
                    emit(Success(offers))
                }
            } else {
                emit(Failure(allOffers.code()))
                Timber.d("Exception: %s", allOffers.code())
            }
        } catch (exception: IOException) {
            emit(Failure(exception.hashCode()))
            Timber.d("IOException: %s", exception.message)
        }
    }.flowOn(backgroundDispatcher)
}