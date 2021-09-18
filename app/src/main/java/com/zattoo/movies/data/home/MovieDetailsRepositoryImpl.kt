package com.zattoo.movies.data.home

import com.zattoo.movies.data.remote.MovieListEntity
import com.zattoo.movies.data.remote.MovieService
import com.zattoo.movies.domain.Failure
import com.zattoo.movies.domain.MovieDetailsRepository
import com.zattoo.movies.domain.Results
import com.zattoo.movies.domain.Success
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
import java.io.IOException

class MovieDetailsRepositoryImpl(
    private val service: MovieService,
    private val backgroundDispatcher: CoroutineDispatcher
) : MovieDetailsRepository {

    override fun getMovieDetail(): Flow<Results<List<MovieListEntity.MovieData>>> = flow {
        try {
            val allMovies = service.fetchMovieList()
            if (allMovies.isSuccessful) {
                if (!allMovies.body()?.movie_data.isNullOrEmpty())
                    emit(Success(allMovies.body()!!.movie_data)) else
                    emit(Failure(EMPTY_LIST))
            } else {
                emit(Failure(allMovies.code()))
                Timber.d("Exception: %s", allMovies.code())
            }
        } catch (exception: IOException) {
            emit(Failure(exception.hashCode()))
            Timber.d("IOException: %s", exception.message)
        }
    }.flowOn(backgroundDispatcher)

    companion object {
        const val EMPTY_LIST = 204
    }
}