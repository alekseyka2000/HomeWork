package com.example.nine_mvm

import android.app.Application
import com.example.nine_mvm.di.domainModule
import com.example.nine_mvm.di.modelModule
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
            modules(modelModule, domainModule)
        }
    }
}