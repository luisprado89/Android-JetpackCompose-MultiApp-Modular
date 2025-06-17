package com.luis.baseexamenjunio.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.luis.baseexamenjunio.ui.components.BottomBar
import com.luis.baseexamenjunio.ui.components.ProductItem
import com.luis.baseexamenjunio.ui.viewmodel.AddOrderViewModel
import com.luis.baseexamenjunio.ui.viewmodel.OrderViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddOrderScreen(
    viewModel: AddOrderViewModel,
    orderViewModel: OrderViewModel,
    onNavigateToOrders: () -> Unit
) {
    val products by viewModel.products.collectAsState()
    val viewMode by viewModel.viewMode.collectAsState()
    val context = LocalContext.current

    Scaffold(
        topBar = { TopAppBar(title = { Text("McAndroid Restaurant") }) },
        bottomBar = {
            BottomBar(
                currentScreen = "add",
                onAddOrderClick = {},
                onOrdersClick = onNavigateToOrders
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            // Selector de vista
            Row(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
                Button(
                    onClick = { viewModel.setViewMode("list") },
                    enabled = viewMode != "list"
                ) {
                    Text("Lista")
                }
                Spacer(Modifier.width(8.dp))
                Button(
                    onClick = { viewModel.setViewMode("grid") },
                    enabled = viewMode != "grid"
                ) {
                    Text("Grilla")
                }
            }

            // Lista/Grid de productos
            if (viewMode == "list") {
                LazyColumn {
                    items(products) { product ->
                        ProductItem(
                            product = product,
                            onIncrement = { viewModel.incrementQuantity(product.name) },
                            onDecrement = { viewModel.decrementQuantity(product.name) }
                        )
                    }
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.weight(1f)
                ) {
                    items(products) { product ->
                        ProductItem(
                            product = product,
                            onIncrement = { viewModel.incrementQuantity(product.name) },
                            onDecrement = { viewModel.decrementQuantity(product.name) }
                        )
                    }
                }
            }

            // Resumen y botón
            val totalQuantity = products.sumOf { it.quantity }
            val totalPrice = products.sumOf { it.totalPrice() }
            Row(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Total: $totalQuantity items - ${String.format("%.2f", totalPrice)}€")
                Spacer(Modifier.weight(1f))
                Button(
                    onClick = {
                        orderViewModel.addOrder(products)
                        viewModel.resetQuantities()
                        Toast.makeText(context, "Pedido añadido", Toast.LENGTH_SHORT).show()
                    },
                    enabled = totalQuantity > 0
                ) {
                    Text("Añadir Pedido")
                }
            }
        }
    }
}