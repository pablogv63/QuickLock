package com.pablogv63.quicklock.application

import android.app.Application
import com.pablogv63.quicklock.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class QuickLockApp: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@QuickLockApp)
            modules(appModule)
        }
    }
}