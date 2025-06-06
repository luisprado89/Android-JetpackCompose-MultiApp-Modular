package com.luis.discosfavoritos2

import android.app.Application
import com.luis.discosfavoritos2.data.AppContainer

class DiscosApplication: Application() {
    lateinit var appContainer: AppContainer

    override fun onCreate() {
        super.onCreate()
        appContainer = AppContainer(this)
    }
}