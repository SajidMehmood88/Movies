package com.zattoo.movies.core.di

import com.zattoo.movies.presentation.home.HomeViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { HomeViewModel(getMoviesUseCase = get()) }
}