package com.test.cars

import android.app.Application
import com.test.cars.di.carsModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            modules(carsModule)
            androidContext(this@MyApplication)
        }
    }
}