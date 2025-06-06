package com.luis.discosfavoritos.ui.add

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.luis.discosfavoritos.ListaDiscosTopAppBar
import com.luis.discosfavoritos.ui.AppViewModelProvider
import com.luis.discosfavoritos.ui.navigation.NavigationDestination
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
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            ListaDiscosTopAppBar(
                title = AddDestination.title,
                canNavigateBack = true,
                navigateUp = onNavigateBack
            )
        },
    ){
        AddBody(
            addUiState = viewModel.addUiState,
            onValueChange = viewModel::updateUiState,
            onAdd = {
                coroutineScope.launch {
                    viewModel.saveDisco()
                }
                onNavigateBack()
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
        modifier = modifier.padding(16.dp).fillMaxSize(),
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
fun DiscoDetailsForm(discoDetails: DiscoDetails, onValueChange: (DiscoDetails) -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        FormRow(label = "Título", value = discoDetails.titulo, onValueChange = { onValueChange(discoDetails.copy(titulo = it)) })
        FormRow(label = "Autor", value = discoDetails.autor, onValueChange = { onValueChange(discoDetails.copy(autor = it)) })
        FormRow(label = "Número de canciones", value = discoDetails.numCanciones, onValueChange = { onValueChange(discoDetails.copy(numCanciones = it)) })
        FormRow(label = "Año de publicación", value = discoDetails.publicacion, onValueChange = { onValueChange(discoDetails.copy(publicacion = it)) })
        RatingRow(label = "Valoración", selectedRating = discoDetails.valoracion.toInt(), onRatingChange = { onValueChange(discoDetails.copy(valoracion = it.toString())) })
    }
}

@Composable
fun FormRow(label: String, value: String, onValueChange: (String) -> Unit) {
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
                modifier = Modifier.fillMaxWidth()
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

@Preview(showBackground = true)
@Composable
fun RattingRowPreview() {
    RatingRow(label = "Valoración", selectedRating = 3, onRatingChange = {})
}