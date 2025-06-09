package com.luis.listacomprapersistente

import android.app.Application
import com.luis.listacomprapersistente.data.AppContainer

class ListaCompraApplication : Application() {
    lateinit var appContainer: AppContainer
    override fun onCreate() {
        super.onCreate()
        appContainer = AppContainer(this)
    }
}