package com.luis.discosfavoritos2.ui.edit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.luis.discosfavoritos2.ui.AppViewModelProvider
import com.luis.discosfavoritos2.ui.add.DiscoDetails
import com.luis.discosfavoritos2.ui.add.DiscoDetailsForm
import com.luis.discosfavoritos2.ui.components.ListaDiscosTopAppBar
import kotlinx.coroutines.launch

object EditDestination : com.luis.discosfavoritos2.ui.navigation.NavigationDestination {
    override val route = "edit"
    override val title = "Editar disco"
    const val editIdArg = "editId"
    val routeWithArgs = "$route/{$editIdArg}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditDiscoScreen(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: EditDiscoViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val state by viewModel.editUiState
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            ListaDiscosTopAppBar(
                title = EditDestination.title,
                canNavigateBack = true,
                navigateBack = onNavigateBack
            )
        },
    ) { padding ->
        Column(
            modifier = modifier.padding(padding)
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            DiscoDetailsForm(
                discoDetails = state.discoDetails,
                onValueChange = viewModel::updateUiState
            )
            Button(
                onClick = {
                    coroutineScope.launch {
                        viewModel.saveDisco()
                        onNavigateBack()//Esto hacer que al hacer click en guardar cambiar vuelva a la pantalla de atras
                    }
                },
                enabled = state.isSaveButtonEnabled,
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text("Guardar cambios")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun EditDiscoScreenPreview() {
    // Estado de edición simulado
    val fakeDetails = DiscoDetails(
        id = 5,
        titulo = "Estopía",
        autor = "Estopa",
        numCanciones = "11",
        publicacion = "2024",
        valoracion = "4"
    )
    val fakeUiState = remember {
        mutableStateOf(
            EditUiState(
                discoDetails = fakeDetails,
                isSaveButtonEnabled = true
            )
        )
    }

    MaterialTheme {
        Scaffold(
            topBar = {
                ListaDiscosTopAppBar(
                    title = "Editar disco",
                    canNavigateBack = true,
                    navigateBack = {}
                )
            },
        ) { padding ->
            Column(
                modifier = Modifier.padding(padding)
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                DiscoDetailsForm(
                    discoDetails = fakeUiState.value.discoDetails,
                    onValueChange = { }
                )
                Button(
                    onClick = { },
                    enabled = true,
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Text("Guardar cambios")
                }
            }
        }
    }
}
