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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
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
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    if (uiState.showError) {
        Toast.makeText(context, uiState.errorMessage, Toast.LENGTH_SHORT).show()
        viewModel.clearError()
    }

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
                    Text("Cantidad: ${uiState.products.sumOf { it.quantity }}", fontSize = 20.sp)
                    Text(
                        "Precio: %.2f".format(uiState.products.sumOf { it.quantity * it.price.toDouble() }),
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
                        value = uiState.nameInput,
                        onValueChange = viewModel::onNameChanged,
                        label = { Text("Nuevo Producto") },
                        singleLine = true,
                        // Quita el fillMaxWidth aquí, no lo necesitas
                    )
                    TextField(
                        value = uiState.priceInput,
                        onValueChange = viewModel::onPriceChanged,
                        label = { Text("Precio") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        // Quita el fillMaxWidth aquí, no lo necesitas
                    )
                }
                IconButton(
                    onClick = {
                        if (uiState.nameInput.isBlank() || uiState.priceInput.isBlank()) {
                            Toast.makeText(
                                context,
                                "Product name or price cannot be empty",
                                Toast.LENGTH_SHORT
                            ).show()
                            return@IconButton
                        }
                        viewModel.addProduct()
                        focusManager.clearFocus()
                    },
                    modifier = Modifier,
                    //.size(56.dp)
                    //.padding(start = 8.dp)
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
                items(uiState.products.size) { index ->
                    val product = uiState.products[index]
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
            IconButton(onClick = delete) {
                Icon(Icons.Filled.Delete, contentDescription = "Delete item")
            }
        }
    }
}
