package com.luis.discosfavoritos

import android.app.Application
import com.luis.discosfavoritos.data.AppContainer

class DiscosApplication: Application() {
    lateinit var appContainer: AppContainer

    override fun onCreate() {
        super.onCreate()
        appContainer = AppContainer(this)
    }
}