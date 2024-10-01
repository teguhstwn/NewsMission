package com.teguh.setiawan.newsmission

import android.app.Application
import com.teguh.setiawan.core.di.databaseModule
import com.teguh.setiawan.core.di.networkModule
import com.teguh.setiawan.core.di.repositoryModule
import com.teguh.setiawan.newsmission.di.useCaseModule
import com.teguh.setiawan.newsmission.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(listOf(
                databaseModule,
                networkModule,
                repositoryModule,
                useCaseModule,
                viewModelModule
            ))
        }
    }
}