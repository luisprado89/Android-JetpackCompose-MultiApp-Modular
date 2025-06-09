package com.luis.discosfavoritos2.ui.add

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.twotone.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.luis.discosfavoritos2.ui.AppViewModelProvider
import com.luis.discosfavoritos2.ui.components.ListaDiscosTopAppBar
import com.luis.discosfavoritos2.ui.navigation.NavigationDestination
import kotlinx.coroutines.launch

object AddDestination : NavigationDestination {
    override val route = "add"
    override val title = "Añadir disco"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddScreen(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AddViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val context = LocalContext.current//  mostrar Toast, Obtiene el contexto actual de la aplicación, permite acceder a recursos y funciones del sistema,

    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            ListaDiscosTopAppBar(
                title = AddDestination.title,
                canNavigateBack = true,
                navigateBack = onNavigateBack
            )
        },
    ){
        AddBody(
            addUiState = viewModel.addUiState,
            onValueChange = viewModel::updateUiState,
            onAdd = {
                coroutineScope.launch {
                //se comenta desde aqui si quisieramos el toast
//                    viewModel.saveDisco()
//                }
//                onNavigateBack()


                //Si quisieramos que lanse un Toas con mensaje de disco ya existe

                val result = viewModel.saveDisco()
                if (result) {
                onNavigateBack()
                } else {
                Toast.makeText(
                    context,
                    "El disco ya existe",
                    Toast.LENGTH_SHORT
                    ).show()
                }
              }


            },
            modifier = modifier.padding(it),

        )
    }
}

@Composable
fun AddBody(
    addUiState: AddUiState,
    onValueChange: (DiscoDetails) -> Unit,
    onAdd: () -> Unit,
    modifier: Modifier
) {
    Column (
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        DiscoDetailsForm(
            discoDetails = addUiState.discoDetails,
            onValueChange = onValueChange
        )
        Button(
            onClick = onAdd,
            enabled = addUiState.isSaveButtonEnabled
        ) {
            Text("Añadir")
        }
    }
}

@Composable
fun DiscoDetailsForm(
    discoDetails: DiscoDetails,
    onValueChange: (DiscoDetails) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        FormRow(
            label = "Título",
            value = discoDetails.titulo,
            onValueChange = { onValueChange(discoDetails.copy(titulo = it)) }
        )
        FormRow(
            label = "Autor",
            value = discoDetails.autor,
            onValueChange = { onValueChange(discoDetails.copy(autor = it)) }
        )
        FormRow(
            label = "Número de canciones",
            value = discoDetails.numCanciones,
            onValueChange = { onValueChange(discoDetails.copy(numCanciones = it.filter { ch -> ch.isDigit() }.take(2))) },
            keyboardType = KeyboardType.Number, // SOLO NÚMEROS, 2 cifras máximo
            errorMessage = numCancionesError(discoDetails.numCanciones)
        )
        FormRow(
            label = "Año de publicación",
            value = discoDetails.publicacion,
            onValueChange = { onValueChange(discoDetails.copy(publicacion = it.filter { ch -> ch.isDigit() }.take(4))) },
            keyboardType = KeyboardType.Number, // SOLO NÚMEROS, 4 cifras máximo
            errorMessage = anioError(discoDetails.publicacion)
        )
        RatingRow(
            label = "Valoración",
            selectedRating = discoDetails.valoracion.toIntOrNull() ?: 1,//La valoración no puede ser menor a 1 ni superior a 5.
            onRatingChange = { onValueChange(discoDetails.copy(valoracion = it.toString())) }
        )
    }
}



@Composable
fun FormRow(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,

    keyboardType: KeyboardType = KeyboardType.Text, // <- Añadido valor por defecto
    errorMessage: String? = null//<- Añadido para mostrar un mensaje de error
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = Modifier.weight(0.3f)) {
            Text(text = label)
        }
        Box(modifier = Modifier.weight(0.7f)) {
            OutlinedTextField(
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = keyboardType),isError = !errorMessage.isNullOrBlank(),
                supportingText = {
                    if (!errorMessage.isNullOrBlank()) {
                        Text(
                            text = errorMessage,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            )
        }
    }
}

@Composable
fun RatingRow(label: String, selectedRating: Int, onRatingChange: (Int) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = Modifier.weight(0.3f)) {
            Text(text = label)
        }
        Box(modifier = Modifier.weight(0.7f)) {
            Row {
                for (i in 1..5) {
                    IconButton(onClick = { onRatingChange(i) }) {
                        if (i <= selectedRating) {
                            Icon(Icons.Filled.Star,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.inversePrimary)
                        } else {
                            Icon(Icons.TwoTone.Star, contentDescription = null,
                                tint = MaterialTheme.colorScheme.inversePrimary)
                        }
                    }
                }
            }
        }
    }
}



// ---- VALIDACIONES PARA LOS CAMPOS ----

fun numCancionesError(numCanciones: String): String? {
    val n = numCanciones.toIntOrNull()
    return when {
        numCanciones.isBlank() -> null
        !numCanciones.all { it.isDigit() } -> "Solo se permiten números"
        n == null -> "Introduce un número"
        n < 1 || n > 99 -> "Entre 1 y 99"
        else -> null
    }
}

fun anioError(anio: String): String? {
    val n = anio.toIntOrNull()
    return when {
        anio.isBlank() -> null
        !anio.all { it.isDigit() } -> "Solo se permiten números"
        n == null -> "Introduce un año"
        n < 1000 || n > 2030 -> "Entre 1000 y 2030"
        else -> null
    }
}
@Preview(showBackground = true)
@Composable
fun RattingRowPreview() {
    RatingRow(label = "Valoración", selectedRating = 3, onRatingChange = {})
}


@Preview(showBackground = true)
@Composable
fun AddBodyPreview() {
    AddBody(
        addUiState = AddUiState(
            discoDetails = DiscoDetails(
                titulo = "Hybrid Theory",
                autor = "Linkin Park",
                numCanciones = "12",
                publicacion = "2000",
                valoracion = "4"
            ),
            isSaveButtonEnabled = true
        ),
        onValueChange = {},
        onAdd = {},
        modifier = Modifier
    )
}
@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun AddScreenPreview() {
    Scaffold(
        topBar = {
            ListaDiscosTopAppBar(
                title = "Añadir disco",
                canNavigateBack = true,
                navigateBack = {}
            )
        }
    ) { padding ->
        AddBody(
            addUiState = AddUiState(
                discoDetails = DiscoDetails(
                    titulo = "Hybrid Theory",
                    autor = "Linkin Park",
                    numCanciones = "12",
                    publicacion = "2000",
                    valoracion = "4"
                ),
                isSaveButtonEnabled = true
            ),
            onValueChange = {},
            onAdd = {},
            modifier = Modifier.padding(padding)
        )
    }
}
