package com.luis.discosfavoritos.ui.add

import android.database.sqlite.SQLiteConstraintException
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import com.luis.discosfavoritos.data.Disco
import com.luis.discosfavoritos.data.DiscoRepository

data class AddUiState(
    val discoDetails: DiscoDetails = DiscoDetails(),
    val isSaveButtonEnabled: Boolean = false
)


data class DiscoDetails(
    val id: Int = 0,
    val titulo: String = "",
    val autor: String = "",
    val numCanciones: String = "",
    val publicacion: String = "",
    var valoracion: String = "1",
)

fun Disco.toDiscoDetails(): DiscoDetails {
    return DiscoDetails(
        id = id,
        titulo = titulo,
        autor = autor,
        numCanciones = numCanciones.toString(),
        publicacion = publicacion.toString(),
        valoracion = valoracion.toString()
    )
}

fun DiscoDetails.toDisco(): Disco {
    return Disco(
        id = id,
        titulo = titulo,
        autor = autor,
        numCanciones = numCanciones.toInt(),
        publicacion = publicacion.toInt(),
        valoracion = valoracion.toInt()
    )
}


class AddViewModel(
    private val discoRepository: DiscoRepository,
) : ViewModel() {
    var addUiState by mutableStateOf(AddUiState())
        private set

    fun updateUiState(discoDetails: DiscoDetails) {
        addUiState = addUiState.copy(discoDetails = discoDetails,
            isSaveButtonEnabled = validateInput(discoDetails))
    }

    suspend fun saveDisco() : Boolean {
        if (validateInput()) {
            try {
                discoRepository.insert(addUiState.discoDetails.toDisco())
                return true
            } catch (e: SQLiteConstraintException) {
                return false
            }
        } else return false
    }

    private fun validateInput(discoDetails: DiscoDetails = addUiState.discoDetails): Boolean {
        return with(discoDetails) {
            discoDetails.titulo.isNotBlank() && discoDetails.autor.isNotBlank()
                    && discoDetails.numCanciones.isNotBlank() && discoDetails.publicacion.isNotBlank()
                    && discoDetails.numCanciones.isDigitsOnly() && discoDetails.publicacion.isDigitsOnly()
                    && discoDetails.numCanciones.toInt() > 0 && discoDetails.publicacion.toInt() > 999
                    && discoDetails.numCanciones.toInt() < 100 && discoDetails.publicacion.toInt() < 2030
        }
    }
}
