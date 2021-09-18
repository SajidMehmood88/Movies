package com.zattoo.movies.data.home.mapper

import com.zattoo.movies.data.remote.MovieListEntity
import com.zattoo.movies.data.remote.MovieListOffers
import com.zattoo.movies.domain.DomainMovies

interface HomeDataMapper {

    fun mapServerResponseToDomainMovies(
        movieDetails: List<MovieListEntity.MovieData>,
        movieOffer: List<MovieListOffers.MovieOffer>,
        imageBase: String
    ): List<DomainMovies>
}