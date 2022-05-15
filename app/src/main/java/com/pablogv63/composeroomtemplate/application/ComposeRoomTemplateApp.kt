package com.pablogv63.composeroomtemplate.application

import android.app.Application
import com.pablogv63.composeroomtemplate.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class ComposeRoomTemplateApp: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@ComposeRoomTemplateApp)
            modules(appModule)
        }
    }
}