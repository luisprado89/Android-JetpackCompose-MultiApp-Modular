package com.luis.discosfavoritos2.ui.edit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luis.discosfavoritos2.data.DiscoRepository
import com.luis.discosfavoritos2.ui.add.DiscoDetails
import com.luis.discosfavoritos2.ui.add.toDisco
import com.luis.discosfavoritos2.ui.add.toDiscoDetails
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

data class EditUiState(
    val discoDetails: DiscoDetails = DiscoDetails(),
    val isSaveButtonEnabled: Boolean = false
)

class EditDiscoViewModel(
    savedStateHandle: SavedStateHandle,
    private val discoRepository: DiscoRepository
) : ViewModel() {

    var editUiState = androidx.compose.runtime.mutableStateOf(EditUiState())
        private set

    private val discoId: Int = checkNotNull(savedStateHandle[EditDestination.editIdArg])

    init {
        val discoDetails = discoRepository.getDisco(discoId).filterNotNull().map {
            it.toDiscoDetails()
        }
        viewModelScope.launch {
            discoDetails.collect {
                updateUiState(it)
            }
        }
    }

    fun updateUiState(discoDetails: DiscoDetails) {
        editUiState.value = editUiState.value.copy(
            discoDetails = discoDetails,
            isSaveButtonEnabled = validateInput(discoDetails)
        )
    }

    suspend fun saveDisco() {
        if (validateInput()) {
            discoRepository.update(editUiState.value.discoDetails.toDisco())
        }
    }

    private fun validateInput(discoDetails: DiscoDetails = editUiState.value.discoDetails): Boolean {
        return discoDetails.titulo.isNotBlank() &&
                discoDetails.autor.isNotBlank() &&
                discoDetails.numCanciones.isNotBlank() &&
                discoDetails.publicacion.isNotBlank() &&
                discoDetails.numCanciones.toIntOrNull() in 1..999 &&
                discoDetails.publicacion.toIntOrNull() in 1000..2029
    }

//    companion object {
//        const val editIdArg = "editId"
//    }
}

