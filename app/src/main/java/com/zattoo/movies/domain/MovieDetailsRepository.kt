package com.zattoo.movies.domain

import com.zattoo.movies.data.remote.MovieListEntity
import kotlinx.coroutines.flow.Flow

interface MovieDetailsRepository {
    fun getMovieDetail(): Flow<Results<List<MovieListEntity.MovieData>>>
}