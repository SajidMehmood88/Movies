package com.zattoo.movies.core.di

import com.zattoo.movies.core.utils.NetworkUtils
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appUtilsModule = module {
    single { NetworkUtils(androidContext()) }
}