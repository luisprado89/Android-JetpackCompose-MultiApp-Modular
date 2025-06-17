package com.luis.baseexamenjunio.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
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
import com.luis.baseexamenjunio.data.OrderState
import com.luis.baseexamenjunio.ui.components.BottomBar
import com.luis.baseexamenjunio.ui.components.OrderCard
import com.luis.baseexamenjunio.ui.viewmodel.OrderManagementViewModel
import androidx.compose.runtime.getValue


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderManagementScreen(
    viewModel: OrderManagementViewModel,
    onNavigateToAddOrder: () -> Unit
) {
    val orders by viewModel.sortedOrders.collectAsState()
    val sortCriteria by viewModel.sortCriteria.collectAsState()
    val context = LocalContext.current

    Scaffold(
        topBar = { TopAppBar(title = { Text("McAndroid Restaurant") }) },
        bottomBar = {
            BottomBar(
                currentScreen = "orders",
                onAddOrderClick = onNavigateToAddOrder,
                onOrdersClick = {}
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            // Selector de orden
            Row(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
                Text("Ordenar por:", modifier = Modifier.align(Alignment.CenterVertically))
                Spacer(Modifier.width(8.dp))
                Button(
                    onClick = { viewModel.setSortCriteria("price") },
                    enabled = sortCriteria != "price"
                ) {
                    Text("Precio")
                }
                Spacer(Modifier.width(8.dp))
                Button(
                    onClick = { viewModel.setSortCriteria("quantity") },
                    enabled = sortCriteria != "quantity"
                ) {
                    Text("Cantidad")
                }
            }

            // Lista de pedidos
            if (orders.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No hay pedidos añadidos")
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(orders) { order ->
                        OrderCard(
                            order = order,
                            onReady = {
                                viewModel.orderViewModel.updateOrderState(order.id, OrderState.READY)
                                Toast.makeText(context, "Pedido listo", Toast.LENGTH_SHORT).show()
                            },
                            onCancel = {
                                viewModel.orderViewModel.updateOrderState(order.id, OrderState.CANCELLED)
                                Toast.makeText(context, "Pedido cancelado", Toast.LENGTH_SHORT).show()
                            },
                            onDelete = {
                                viewModel.orderViewModel.deleteOrder(order.id)
                                Toast.makeText(context, "Pedido eliminado", Toast.LENGTH_SHORT).show()
                            }
                        )
                    }
                }
            }
        }
    }
}