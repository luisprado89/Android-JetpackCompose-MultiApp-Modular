package com.luis.discosfavoritos2.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.luis.discosfavoritos2.DiscosApplication
import com.luis.discosfavoritos2.ui.add.AddViewModel
import com.luis.discosfavoritos2.ui.detail.DetailViewModel
import com.luis.discosfavoritos2.ui.edit.EditDiscoViewModel
import com.luis.discosfavoritos2.ui.home.HomeViewModel

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
        initializer {
            EditDiscoViewModel(
                this.createSavedStateHandle(),
                discosApp().appContainer.provideDiscoRepository()
            )
        }

    }
}

fun CreationExtras.discosApp(): DiscosApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as DiscosApplication)