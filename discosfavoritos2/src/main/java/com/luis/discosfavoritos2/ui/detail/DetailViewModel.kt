package com.luis.discosfavoritos2.ui.detail

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luis.discosfavoritos2.data.DiscoRepository
import com.luis.discosfavoritos2.ui.add.DiscoDetails
import com.luis.discosfavoritos2.ui.add.toDiscoDetails
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

data class DetailUiState(
    val discoDetails: DiscoDetails = DiscoDetails(),
)

class DetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val discoRepository: DiscoRepository
) : ViewModel() {

//    companion object {
//        const val editIdArg = "editId"
//    }
    // Estado que contiene los detalles del disco mostrado en la pantalla
    var discoDetailUiState by mutableStateOf(DetailUiState())
        private set
    // Obtiene el id del disco desde los argumentos de navegación
    private val discoId: Int = checkNotNull(savedStateHandle[DetailDestination.detailIdArg])
    // Actualiza el estado del disco en la UI
    private fun updateUiState(details: DiscoDetails) {
        discoDetailUiState = DetailUiState(details)
    }

    init {
        //Log.d("DetailViewModel", "discoId: $discoId") //Solo sirve para mostrar por consola el Log
        val discoDetails = discoRepository.getDisco(discoId).filterNotNull().map {
            it.toDiscoDetails()
        }
        viewModelScope.launch {
            discoDetails.collect {
                updateUiState(it)
            }
        }
    }



}

