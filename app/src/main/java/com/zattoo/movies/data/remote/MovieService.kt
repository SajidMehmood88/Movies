package com.zattoo.movies.data.remote

import retrofit2.Response
import retrofit2.http.GET

interface MovieService {

    @GET("movie_offers.json")
    suspend fun fetchMovieListOffers(): Response<MovieListOffers>

    @GET("movie_data.json")
    suspend fun fetchMovieList(): Response<MovieListEntity>
}