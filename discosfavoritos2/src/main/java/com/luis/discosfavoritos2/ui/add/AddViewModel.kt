package com.luis.discosfavoritos2.ui.add

import android.database.sqlite.SQLiteConstraintException
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import com.luis.discosfavoritos2.data.Disco
import com.luis.discosfavoritos2.data.DiscoRepository

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

    private fun validateInput(discoDetails: DiscoDetails = addUiState.discoDetails): Boolean {
        return with(discoDetails) {
/*
■ El título y el autor no pueden estar vacíos ni estar en blanco.
■ El número de canciones no puede ser menor a 1 ni superior a 99.
■ El año de publicación no puede ser anterior al año 1000 ni posterior al año 2030.
■ La valoración no puede ser menor a 1 ni superior a 5.
■ El número de canciones, el año de publicación y la valoración sólo pueden contener dígitos.
*/
            discoDetails.titulo.isNotBlank() && discoDetails.autor.isNotBlank()
                    && discoDetails.numCanciones.isNotBlank() && discoDetails.publicacion.isNotBlank()
                    && discoDetails.numCanciones.isDigitsOnly() && discoDetails.publicacion.isDigitsOnly()
                    && discoDetails.numCanciones.toInt() >= 1 && discoDetails.numCanciones.toInt() <= 99
                    && discoDetails.publicacion.toInt() >= 1000  && discoDetails.publicacion.toInt() <= 2030
        }
    }



    fun updateUiState(discoDetails: DiscoDetails) {
        addUiState = addUiState.copy(discoDetails = discoDetails,
            isSaveButtonEnabled = validateInput(discoDetails))
    }
//Este  saveDisco No comprueba si existe el disco con los mismos datos
/*    suspend fun saveDisco() : Boolean {
        if (validateInput()) {
            try {
                discoRepository.insert(addUiState.discoDetails.toDisco())
                return true
            } catch (e: SQLiteConstraintException) {
                return false
            }
        } else return false
    }
*/
    // ✅ Modificado para comprobar si ya existe antes de insertar
    suspend fun saveDisco(): Boolean {
        if (!validateInput()) return false

        val nuevoDisco = addUiState.discoDetails.toDisco()

        // Comprobación manual de duplicado
        if (discoRepository.discoYaExiste(nuevoDisco)) {
        return false
        }

        // Inserta si no existe
        discoRepository.insert(nuevoDisco)
        return true
    }



}
