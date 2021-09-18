package com.zattoo.movies.domain

import com.zattoo.movies.data.remote.MovieListOffers
import kotlinx.coroutines.flow.Flow

interface MovieOffersRepository {
    fun getMovieOffer(): Flow<Results<MovieListOffers>>
}