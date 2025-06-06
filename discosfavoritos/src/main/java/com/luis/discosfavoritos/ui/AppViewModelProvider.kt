package com.luis.discosfavoritos.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.luis.discosfavoritos.DiscosApplication
import com.luis.discosfavoritos.ui.add.AddViewModel
import com.luis.discosfavoritos.ui.detail.DetailViewModel
import com.luis.discosfavoritos.ui.home.HomeViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            HomeViewModel(discosApp().appContainer.provideDiscoRepository())
        }
        initializer {
            AddViewModel(discosApp().appContainer.provideDiscoRepository())
        }
        initializer {
            DetailViewModel(
                this.createSavedStateHandle(),
                discosApp().appContainer.provideDiscoRepository()
            )
        }
    }
}

fun CreationExtras.discosApp(): DiscosApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as DiscosApplication)