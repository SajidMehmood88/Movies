package com.zattoo.movies.domain.home

import com.zattoo.movies.domain.DomainMovies
import com.zattoo.movies.domain.Results
import kotlinx.coroutines.flow.Flow

interface GetMoviesUseCase {
    suspend fun execute(): Flow<Results<List<DomainMovies>>>
}