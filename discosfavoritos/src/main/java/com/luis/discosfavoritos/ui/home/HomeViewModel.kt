package com.luis.discosfavoritos.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luis.discosfavoritos.data.Disco
import com.luis.discosfavoritos.data.DiscoRepository
import com.luis.discosfavoritos.data.startingDiscos
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.math.pow

data class HomeUiState(
    val discoList: List<Disco> = listOf(),
    val valoracionMedia: Double = 0.0,
    val showDeleteDialog: Boolean = false,
    val discoToDelete: Disco? = null
)

class HomeViewModel(
    private val discoRepository: DiscoRepository
) : ViewModel() {
    private val _homeUiState = MutableStateFlow(HomeUiState())
    val homeUiState: StateFlow<HomeUiState> = _homeUiState.asStateFlow()

    init {
        viewModelScope.launch {
            discoRepository.getAll().map { discos ->
                var valoracionMedia = discos.map { it.valoracion.toDouble() }.average()
                // Limitamos los decimales de valoracionMedia a 2
                valoracionMedia = (valoracionMedia * 10.0.pow(2)).toLong() / 10.0.pow(2)
                HomeUiState(discoList = discos, valoracionMedia = valoracionMedia)
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = HomeUiState()
            ).collect {
                _homeUiState.value = it
            }
        }
    }

    fun onShowOrHideDeleteDialog(show: Boolean, disco: Disco) {
        _homeUiState.value = _homeUiState.value.copy(showDeleteDialog = show, discoToDelete = disco)

    }

    fun deleteDisco(disco: Disco) {
        viewModelScope.launch {
            discoRepository.delete(disco)
        }
    }

    fun insertarDiscosDePrueba() {
        viewModelScope.launch {
            for (discos in startingDiscos) {
                discoRepository.insert(discos)
            }
        }
    }
    companion object {
        private const val TIMEOUT_MILLIS = 5000L
    }
}

