package com.zattoo.movies.data.home.mapper

import com.zattoo.movies.data.remote.*
import com.zattoo.movies.domain.DomainMovies

class HomeDataMapperImpl : HomeDataMapper {

    override fun mapServerResponseToDomainMovies(
        movieDetails: List<MovieListEntity.MovieData>,
        movieOffer: List<MovieListOffers.MovieOffer>,
        imageBase: String
    ): List<DomainMovies> {
        return movieOffer.mapNotNull { offers ->
            val movieDetails = movieDetails.find { it.movie_id == offers.movie_id }
            movieDetails?.let {
                val movieOfferPrice = offers.price
                val currency = Currency(movieOfferPrice.last().toString())
                val price = movieOfferPrice.substring(0 until movieOfferPrice.length - 1).toFloat()
                DomainMovies(
                    id = it.movie_id,
                    title = it.title,
                    subtitle = it.sub_title,
                    price = Price(price, currency),
                    image = Image(imageBase + offers.image),
                    availability = offers.available
                )
            }
        }
    }
}