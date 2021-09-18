package com.zattoo.movies.core.di

import com.zattoo.movies.domain.home.GetMoviesUseCase
import com.zattoo.movies.domain.home.GetMoviesUseCaseImpl
import org.koin.dsl.module

val domainModule = module {

    single {
        GetMoviesUseCaseImpl(
            mapper = get(), movieDetailRepository = get(),
            movieOfferRepository = get()
        )
    }

    single<GetMoviesUseCase> {
        GetMoviesUseCaseImpl(
            mapper = get(), movieDetailRepository = get(),
            movieOfferRepository = get()
        )
    }
}