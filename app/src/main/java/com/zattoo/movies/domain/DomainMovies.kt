package com.zattoo.movies.domain

import com.zattoo.movies.data.remote.Image
import com.zattoo.movies.data.remote.Price

data class DomainMovies(
    val title: String,
    val subtitle: String,
    val price: Price,
    val image: Image,
    val availability: Boolean,
    val id: Int
)
