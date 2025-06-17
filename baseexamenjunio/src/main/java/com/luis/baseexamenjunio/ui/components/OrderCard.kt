package com.luis.baseexamenjunio.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.luis.baseexamenjunio.data.Order
import com.luis.baseexamenjunio.data.OrderState

@Composable
fun OrderCard(
    order: Order,
    onReady: () -> Unit,
    onCancel: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor = when (order.state) {
        OrderState.READY -> Color.Green.copy(alpha = 0.2f)
        OrderState.CANCELLED -> Color.Red.copy(alpha = 0.2f)
        else -> MaterialTheme.colorScheme.surface
    }

    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("ID: ${order.id}", style = MaterialTheme.typography.titleMedium)
            Text("Total: ${order.totalPrice()}€")
            Text("Productos: ${order.totalQuantity()}")

            Row(modifier = Modifier.fillMaxWidth()) {
                if (order.state == OrderState.IN_PROGRESS) {
                    IconButton(onClick = onReady) {
                        Icon(Icons.Default.Check, "Listo", tint = Color.Green)
                    }
                    IconButton(onClick = onCancel) {
                        Icon(Icons.Default.Close, "Cancelar", tint = Color.Red)
                    }
                }
                IconButton(
                    onClick = onDelete,
                    modifier = Modifier.fillMaxWidth().weight(1f)
                ) {
                    Icon(
                        Icons.Default.Delete, "Eliminar")
                }
            }
        }
    }
}