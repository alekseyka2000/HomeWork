package com.example.nine_mvp

import android.app.Application
import com.example.nine_mvp.di.domainModule
import com.example.nine_mvp.di.modelModule
import com.example.nine_mvp.di.presenterModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(modelModule, domainModule, presenterModule)
        }
    }
}