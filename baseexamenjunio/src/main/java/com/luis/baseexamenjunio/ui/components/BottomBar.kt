package com.luis.baseexamenjunio.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun BottomBar(
    currentScreen: String,
    onAddOrderClick: () -> Unit,
    onOrdersClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(
            onClick = onAddOrderClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = if (currentScreen == "add") MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.surface
            )
        ) {
            Text("Nuevo Pedido")
        }
        Button(
            onClick = onOrdersClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = if (currentScreen == "orders") MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.surface
            )
        ) {
            Text("Pedidos")
        }
    }
}