package com.zattoo.movies.core.di

import com.zattoo.movies.domain.MovieDetailsRepository
import com.zattoo.movies.data.home.MovieDetailsRepositoryImpl
import com.zattoo.movies.domain.MovieOffersRepository
import com.zattoo.movies.data.home.MovieOffersRepositoryImpl
import com.zattoo.movies.data.home.mapper.HomeDataMapper
import com.zattoo.movies.data.home.mapper.HomeDataMapperImpl
import com.zattoo.movies.data.remote.MovieService
import kotlinx.coroutines.Dispatchers
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

private const val BASE_URL = "https://movies-assessment.firebaseapp.com/"
private const val BACKGROUND_DISPATCHER = "background_dispatcher"

val dataModule = module {
    single { createNetworkClient(BASE_URL) }

    single { get<Retrofit>().create(MovieService::class.java) }

    single(named(BACKGROUND_DISPATCHER)) { Dispatchers.IO }

    single<HomeDataMapper> { HomeDataMapperImpl() }

    single { MovieDetailsRepositoryImpl(service = get(), get(named(BACKGROUND_DISPATCHER))) }

    single<MovieDetailsRepository> {
        MovieDetailsRepositoryImpl(
            service = get(), get(
                named(
                    BACKGROUND_DISPATCHER
                )
            )
        )
    }

    single { MovieOffersRepositoryImpl(service = get(), get(named(BACKGROUND_DISPATCHER))) }

    single<MovieOffersRepository> {
        MovieOffersRepositoryImpl(
            service = get(), get(
                named(
                    BACKGROUND_DISPATCHER
                )
            )
        )
    }
}