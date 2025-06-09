package com.luis.listacompraexamenbd.ui

import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.luis.listacompraexamenbd.ui.viewmodel.ProductListViewModel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import com.luis.listacompraexamenbd.ListaCompraExamenBDApp
import com.luis.listacompraexamenbd.ui.viewmodel.ProductDetailViewModel

fun CreationExtras.listaCompraApp(): ListaCompraExamenBDApp =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as ListaCompraExamenBDApp)


object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            ProductListViewModel(listaCompraApp().container.provideProductRepository())
        }

        initializer {
            ProductDetailViewModel(
                this.createSavedStateHandle(),
                listaCompraApp().container.provideProductRepository()
            )
        }
    }
}