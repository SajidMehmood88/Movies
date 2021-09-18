package com.zattoo.movies

import android.app.Application
import com.zattoo.movies.core.di.appUtilsModule
import com.zattoo.movies.core.di.dataModule
import com.zattoo.movies.core.di.domainModule
import com.zattoo.movies.core.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class MoviesApp : Application() {

    override fun onCreate() {
        super.onCreate()
        setup()
    }

    private fun setup() {
        Timber.plant(Timber.DebugTree())
        startKoin {
            androidContext(this@MoviesApp)
            modules(
                listOf(
                    dataModule,
                    domainModule,
                    viewModelModule,
                    appUtilsModule
                )
            )
        }
    }
}