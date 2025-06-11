package com.luis.listacompraexamenbd.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.luis.listacompraexamenbd.ui.AppViewModelProvider
import com.luis.listacompraexamenbd.ui.navigation.NavigationDestination
import com.luis.listacompraexamenbd.ui.viewmodel.ProductListViewModel
import kotlin.collections.sumOf
import kotlin.text.format
import kotlin.text.isBlank

object ListaCompraDestination : NavigationDestination {
    override val route = "listacompra"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaCompraScreen(
    onNavigateBack: () -> Unit,
    onNavigateToDetail: (String) -> Unit,
    viewModel: ProductListViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val state by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Shopping List", fontSize = 24.sp) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                ),
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Total", fontSize = 20.sp)
                    Text("Cantidad: ${state.products.sumOf { it.quantity }}", fontSize = 20.sp)
                    Text(
                        "Precio: %.2f".format(state.products.sumOf { it.quantity * it.price.toDouble() }),
                        fontSize = 20.sp
                    )


                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(10.dp),
            verticalArrangement = Arrangement.SpaceAround,// Distribuye los elementos con espacio entre ellos, alrededor
            horizontalAlignment = Alignment.CenterHorizontally,// Centra los elementos horizontalmente

        ) {
            // FORMULARIO de añadir producto
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column(
                    modifier = Modifier
                        //.weight(1f) // <-- Esto permite que el IconButton tenga espacio visible
                        .padding(8.dp)
                ) {
                    TextField(
                        value = state.nameInput,
                        onValueChange = viewModel::onNameChanged,
                        label = { Text("Nuevo Producto") },
                        singleLine = true,

                    )
                    TextField(
                        value = state.priceInput,
                        onValueChange = viewModel::onPriceChanged,
                        label = { Text("Precio") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),

                    )
                }

                IconButton(
                    onClick = {
                        val name = state.nameInput.trim()
                        val priceText = state.priceInput.trim()

                        // 1) Campos vacíos
                        if (name.isBlank() || priceText.isBlank()) {
                            Toast.makeText(context,
                                "Producto nombre o precio no puede estar vacío",
                                Toast.LENGTH_SHORT).show()
                            return@IconButton
                        }

                        // 2) Precio numérico y > 0
                        val price = priceText.toFloatOrNull()
                        if (price == null || price <= 0f) {
                            Toast.makeText(context,
                                "Introduce un precio válido mayor que 0",
                                Toast.LENGTH_SHORT).show()
                            return@IconButton
                        }

                        // 3) Duplicado de esta manera ignora si es mayusculas o minusculas
                        if (state.products.any { it.name.equals(name, true) }) {
                            Toast.makeText(context,
                                "El producto ya existe en la lista",
                                Toast.LENGTH_SHORT).show()
                            return@IconButton
                        }

                        // 4) Todo OK → insertamos
                        viewModel.addProduct()
                        focusManager.clearFocus()
                    },
                    //modifier = Modifier,

                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "Add item",
                        modifier = Modifier.size(32.dp)
                    )
                }
            }

            // Botón Aleatorio
            Button(
                onClick = { viewModel.addRandomProduct() },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 8.dp)
            ) {
                Text("Aleatorio")
                Spacer(Modifier.size(8.dp))
                Icon(Icons.Filled.Add, contentDescription = "Add item")
            }

            // LISTA DE PRODUCTOS
            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                items(state.products.size) { index ->
                    val product = state.products[index]
                    ShoppingListItem(
                        name = product.name,
                        quantity = product.quantity.toString(),
                        increaseQuantity = { viewModel.increaseQuantity(product) },
                        decreaseQuantity = { viewModel.decreaseQuantity(product) },
                        details = { onNavigateToDetail(product.name) },
                        delete = { viewModel.deleteProduct(product) }
                    )
                }
            }
        }
    }
}

@Composable
fun ShoppingListItem(
    name: String,
    quantity: String,
    increaseQuantity: () -> Unit,
    decreaseQuantity: () -> Unit,
    details: () -> Unit,
    delete: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(text = name)
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            IconButton(onClick = decreaseQuantity) {
                Icon(Icons.Filled.KeyboardArrowDown, contentDescription = "Decrease item")
            }
            Text(text = quantity, fontSize = 20.sp)
            IconButton(onClick = increaseQuantity) {
                Icon(Icons.Filled.KeyboardArrowUp, contentDescription = "Increase item")
            }
            Spacer(modifier = Modifier.width(16.dp))
            IconButton(onClick = details) {
                Icon(Icons.Filled.Info, contentDescription = "Details")
            }
            //Este seria sin el dialogo simplemente borraria el producto
//            IconButton(onClick = delete) {
//                Icon(Icons.Filled.Delete, contentDescription = "Delete item")
//            }


            // aquí llamamos al botón que muestra el diálogo, pasándole el nombre
            ConfirmDeleteButton(
                name = name,
                onConfirmDelete = delete
            )



        }
    }
}


//Creamos un
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfirmDeleteButton(
    name: String,
    onConfirmDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showDialog by remember { mutableStateOf(false) }

    IconButton(
        onClick = { showDialog = true },
        modifier = modifier
    ) {
        Icon(
            imageVector = Icons.Filled.Delete,
            contentDescription = "Eliminar item $name"
        )
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Confirmación de borrado") },
            text = { Text("¿Estás seguro de que quieres eliminar “$name”?") },
            confirmButton = {
                TextButton(onClick = {
                    onConfirmDelete()
                    showDialog = false
                }) {
                    Text("Borrar")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
}
@Preview(showBackground = true, widthDp = 320)
@Composable
fun ConfirmDeleteDialogPreview() {
    MaterialTheme {
        AlertDialog(
            onDismissRequest = { /* Nada */ },
            title = { Text("Confirmación de borrado") },
            text = {
                Text(
                    text = "¿Estás seguro de que quieres eliminar “Cerveza”?",
                    // para que haga salto de línea si es muy largo
                    modifier = Modifier.padding(top = 4.dp, bottom = 8.dp)
                )
            },
            confirmButton = {
                TextButton(onClick = { /* Acción simulada */ }) {
                    Text("Borrar")
                }
            },
            dismissButton = {
                TextButton(onClick = { /* Acción simulada */ }) {
                    Text("Cancelar")
                }
            }
        )
    }
}
