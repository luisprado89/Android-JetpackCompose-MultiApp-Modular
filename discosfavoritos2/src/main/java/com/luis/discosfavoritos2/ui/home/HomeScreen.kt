package com.luis.discosfavoritos2.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.twotone.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.luis.discosfavoritos2.R
import com.luis.discosfavoritos2.data.Disco
import com.luis.discosfavoritos2.ui.AppViewModelProvider
import com.luis.discosfavoritos2.ui.components.ListaDiscosTopAppBar
import com.luis.discosfavoritos2.ui.navigation.NavigationDestination
import kotlinx.coroutines.coroutineScope

object HomeDestination : NavigationDestination {
    override val route = "home"
    override val title = "DiscosApp"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToAdd: () -> Unit,
    onNavigateToDetail: (Int) -> Unit,


    onNavigateToEdit: (Int) -> Unit,   // <--- AÑADE ESTE para editar


    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val state by viewModel.homeUiState.collectAsState()
    // Comportamiento del scroll para la TopAppBar
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()



    //Para poder aplicar el filtro
    val searchText by viewModel.searchText.collectAsState()

    // Filtrar lista según búsqueda
    val filteredList = state.discoList.filter {
        it.titulo.contains(searchText, ignoreCase = true)
    }


    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            //Estamos usando el @Composable
            //fun ListaDiscosTopAppBar(  que esta en ListaDiscosTopAppBar.kt
            ListaDiscosTopAppBar(
                title = HomeDestination.title,
                canNavigateBack = false,//Cuando sea true muestra el boton de Back
                scrollBehavior = scrollBehavior
            )

        /*  Si nos pidiera reutilizar el mismo composable para la barra superior en todas las
        pantallas, pasandole el titulo cuando este cambia y un booleano para saber si tiene que
        mostrar el icono de navegar hacia la pantalla anterior




            CenterAlignedTopAppBar(
                title = { Text("DiscosApp-Luis") },
                modifier = modifier,
                scrollBehavior = scrollBehavior,
                colors = TopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    scrolledContainerColor = MaterialTheme.colorScheme.primary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
         */










        },
        bottomBar = {
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Row (
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    if (state.discoList.isEmpty()) {
                        Text("No hay discos añadidos todavía")
                        //Text(stringResource(R.string.no_discos))
                    } else {
                        Text(
                            "Valoración media: ${state.valoracionMedia}",
                            style = MaterialTheme.typography.titleLarge,)
                    }
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNavigateToAdd, //Con este navega a la AddScreen
                shape = MaterialTheme.shapes.medium
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Nuevo Disco"
                    //contentDescription = stringResource(R.string.disco_entry_title)
                )
            }
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {

            // Solo mostrar el buscador si hay discos en la lista
            if (state.discoList.isNotEmpty()) {
                OutlinedTextField(
                    value = searchText,
                    onValueChange = { viewModel.onSearchTextChange(it) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    label = { Text("Buscar disco por título") },
                    singleLine = true
                )
            }
            HomeBody(
                discos = filteredList,
                onNavigateToDetail = onNavigateToDetail,

                onNavigateToEdit = onNavigateToEdit,  // <-- Añadido para editar

                insertarDiscosDePrueba = viewModel::insertarDiscosDePrueba,
                //onShowOrHideDeleteDialog = viewModel::onShowOrHideDeleteDialog,
                onDelete = viewModel::deleteDisco,
                //onDelete = { disco -> viewModel.deleteDisco(disco)},

                modifier = Modifier
            )
        }

/*  Este es el original sin barra de busqueda
        HomeBody(
            discos = state.discoList,
            onNavigateToDetail = onNavigateToDetail,


            onNavigateToEdit = onNavigateToEdit,  // <-- Añadido para editar


            insertarDiscosDePrueba = viewModel::insertarDiscosDePrueba,
            onShowOrHideDeleteDialog = viewModel::onShowOrHideDeleteDialog,
            modifier = Modifier.padding(innerPadding)
        )
*/

//        if (state.showDeleteDialog) {
//            DeleteConfirmationDialog(
//                title = state.discoToDelete?.titulo ?: "",
//                onDeleteConfirm = {
//                    viewModel.deleteDisco(state.discoToDelete!!)
//                },
//                onDismiss = {
//                    viewModel.onShowOrHideDeleteDialog(false, state.discoToDelete!!)
//                }
//            )
//        }
    }
}

@Composable
fun HomeBody(
    discos: List<Disco>,
    modifier: Modifier,
    insertarDiscosDePrueba: () -> Unit = {},
    onNavigateToDetail: (Int) -> Unit,


    onNavigateToEdit: (Int) -> Unit,    // <--- Añadido para editar

    onDelete: (Disco) -> Unit
    //onShowOrHideDeleteDialog: (Boolean, Disco) -> Unit,
) {
    if (discos.isEmpty()){
        Column (
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(R.string.no_discos),
                style = MaterialTheme.typography.titleLarge
            )
            Icon(
                imageVector = Icons.Default.Clear,
                contentDescription = "Nuevo disco",
                modifier = Modifier.padding(16.dp).size(64.dp)
            )
            Button(
                onClick = insertarDiscosDePrueba,
            ) {
                Text(stringResource(R.string.insert_test_discos))
            }
        }
    } else {
        LazyColumn(
            modifier = modifier
        ) {
            items(discos) {
                DiscoListItem(
                    disco = it,
                    onNavigateToDetail = onNavigateToDetail,


                    onNavigateToEdit = onNavigateToEdit,//Este le añadimos para nevedar


                    onDelete = onDelete
                    //onShowOrHideDeleteDialog = onShowOrHideDeleteDialog

                )
            }
        }
    }
}

@Composable
fun DiscoListItem(
    disco: Disco,
    onNavigateToDetail: (Int) -> Unit,
    //onShowOrHideDeleteDialog: (Boolean, Disco) -> Unit,

    onNavigateToEdit: (Int) -> Unit,         // <--- Añadido

    onDelete:  (Disco) -> Unit,

) {
    Row (
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.inversePrimary)
            .padding(8.dp)
            .clickable(
                onClick = { onNavigateToDetail(disco.id) }// Este es el que le dice que al hacer click en la fila naveve hacia DetailScreen
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Column (
            modifier = Modifier.weight(0.4f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Text(text = disco.titulo,
                style = MaterialTheme.typography.titleMedium)
            Text(text = disco.autor,
                style = MaterialTheme.typography.titleSmall)
        }//Column
        Row (
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            for (i in 1..5) {
                Icon(
                    imageVector = if (i <= disco.valoracion) Icons.Filled.Star else Icons.TwoTone.Star,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }//Row

//        IconButton(
//            onClick = {
//                onShowOrHideDeleteDialog(true, disco)
//            }
//        ) {
//            Icon(
//                imageVector = Icons.Default.Delete,
//                contentDescription = stringResource(R.string.delete_button),
//                tint = MaterialTheme.colorScheme.primary
//            )
//        }
        //Con esto usamos el composable
        ConfirmDeleteButton(
            name = disco.titulo,
            onConfirmDelete = { onDelete(disco) }
        )




        //Añadimos un boton para ir a editar
        IconButton(
            onClick = { onNavigateToEdit(disco.id) }
        ) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Editar disco",
                tint = MaterialTheme.colorScheme.primary
            )
        }

    }// Row el que contiene todo
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfirmDeleteButton(
    name: String,
    onConfirmDelete: () -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }

    // Botón de papelera
    IconButton(onClick = { showDialog = true }) {
        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = stringResource(R.string.delete_button, name)
        )
    }

    // Diálogo de confirmación
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(stringResource(R.string.delete_dialog_title)) },
            text = { Text(stringResource(R.string.delete_dialog_message, name)) },
            confirmButton = {
                TextButton(onClick = {
                    onConfirmDelete()
                    showDialog = false
                }) {
                    Text(stringResource(R.string.delete_dialog_confirm_button))
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text(stringResource(R.string.delete_dialog_dismiss_button))
                }
            }
        )
    }
}

//
//@Preview(showBackground = true)
//@Composable
//fun HomeScreenPreview() {
//    val fakeDiscos = listOf(
//        Disco(id = 1, titulo = "Estopa", autor = "Estopa", numCanciones = 12, publicacion = 1999, valoracion = 5),
//        Disco(id = 2, titulo = "Lemonade", autor = "Beyoncé", numCanciones = 12, publicacion = 2016, valoracion = 4),
//        Disco(id = 3, titulo = "The Fame", autor = "Lady Gaga", numCanciones = 12, publicacion = 2008, valoracion = 3)
//    )
//
//    MaterialTheme {
//        HomeBody(
//            discos = fakeDiscos,
//            onNavigateToDetail = {},
//            onNavigateToEdit = {},
//            onShowOrHideDeleteDialog = { _, _ -> },
//            insertarDiscosDePrueba = {},
//            modifier = Modifier
//        )
//    }
//}
//
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Preview(showBackground = true)
//@Composable
//fun HomeScreenFullPreview() {
//    val fakeDiscos = listOf(
//        Disco(id = 1, titulo = "Estopa", autor = "Estopa", numCanciones = 12, publicacion = 1999, valoracion = 5),
//        Disco(id = 2, titulo = "Lemonade", autor = "Beyoncé", numCanciones = 12, publicacion = 2016, valoracion = 4),
//        Disco(id = 3, titulo = "The Fame", autor = "Lady Gaga", numCanciones = 12, publicacion = 2008, valoracion = 3)
//    )
//
//    val fakeState = HomeUiState(
//        discoList = fakeDiscos,
//        //valoracionMedia = 4.0,
//        valoracionMedia = "8.50",
//        showDeleteDialog = false
//    )
//
//    // No usamos ViewModel aquí, solo llamamos directamente a HomeBody con Scaffold simulado
//    Scaffold(
//        topBar = {
//            ListaDiscosTopAppBar(
//                title = HomeDestination.title,
//                canNavigateBack = false
//            )
//        },
//        bottomBar = {
//            BottomAppBar(
//                containerColor = MaterialTheme.colorScheme.primary,
//                contentColor = MaterialTheme.colorScheme.onPrimary
//            ) {
//                Row (
//                    modifier = Modifier.fillMaxSize(),
//                    horizontalArrangement = Arrangement.Center,
//                    verticalAlignment = Alignment.CenterVertically
//                ){
//                    Text(
//                        text = stringResource(R.string.average_rating, fakeState.valoracionMedia.toString()),
//                        style = MaterialTheme.typography.titleLarge
//                    )
//                }
//            }
//        },
//        floatingActionButton = {
//            FloatingActionButton(onClick = {}) {
//                Icon(Icons.Default.Add, contentDescription = null)
//            }
//        }
//    ) { innerPadding ->
//        HomeBody(
//            discos = fakeState.discoList,
//            onNavigateToDetail = {},
//            onNavigateToEdit = {},
//            onShowOrHideDeleteDialog = { _, _ -> },
//            insertarDiscosDePrueba = {},
//            modifier = Modifier.padding(innerPadding)
//        )
//    }
//}
//@Preview(showBackground = true)
//@Composable
//fun HomeBodySinDiscosPreview() {
//    MaterialTheme {
//        HomeBody(
//            discos = emptyList(), // No hay discos
//            onNavigateToDetail = {},
//            onNavigateToEdit = {},
//            onShowOrHideDeleteDialog = { _, _ -> },
//            insertarDiscosDePrueba = {}, // puedes poner { println("Insertar prueba") } si quieres ver acción en debug
//            modifier = Modifier
//        )
//    }
//}
//
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Preview(showBackground = true)
//@Composable
//fun HomeScreenSinDiscosPreview() {
//    val fakeState = HomeUiState(
//        discoList = emptyList(),          // Simula que no hay discos
//        //valoracionMedia = 0.0,
//        valoracionMedia = "0.0",
//        showDeleteDialog = false
//    )
//
//    Scaffold(
//        topBar = {
//            ListaDiscosTopAppBar(
//                title = HomeDestination.title,
//                canNavigateBack = false
//            )
//        },
//        bottomBar = {
//            BottomAppBar(
//                containerColor = MaterialTheme.colorScheme.primary,
//                contentColor = MaterialTheme.colorScheme.onPrimary
//            ) {
//                Row (
//                    modifier = Modifier.fillMaxSize(),
//                    horizontalArrangement = Arrangement.Center,
//                    verticalAlignment = Alignment.CenterVertically
//                ){
//                    Text(
//                        text = stringResource(R.string.no_discos),
//                        style = MaterialTheme.typography.titleLarge
//                    )
//                }
//            }
//        },
//        floatingActionButton = {
//            FloatingActionButton(onClick = {}) {
//                Icon(Icons.Default.Add, contentDescription = null)
//            }
//        }
//    ) { innerPadding ->
//        HomeBody(
//            discos = fakeState.discoList,
//            onNavigateToDetail = {},
//            onNavigateToEdit = {},
//            onShowOrHideDeleteDialog = { _, _ -> },
//            insertarDiscosDePrueba = {},  // puedes probar con { println("Insertar prueba") }
//            modifier = Modifier.padding(innerPadding)
//        )
//    }
//}
