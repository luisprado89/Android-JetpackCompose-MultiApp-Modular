package com.luis.listacompraexamenbd


import android.app.Application
import com.luis.listacompraexamenbd.data.AppContainer

class ListaCompraExamenBDApp : Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = AppContainer(this)
    }
}
