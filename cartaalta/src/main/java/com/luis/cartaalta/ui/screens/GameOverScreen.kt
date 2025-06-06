package com.luis.cartaalta.ui.screens
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
// Función composable que representa la pantalla de "Game Over"
@Composable
fun GameOverScreen(
    ganador: String, // Nombre del jugador ganador o "Empate"
    onRestart: () -> Unit // Función que se ejecuta al pulsar el botón de reinicio
) {
    Column(
        modifier = Modifier.fillMaxSize(), // Ocupa todo el tamaño disponible en la pantalla
        horizontalAlignment = Alignment.CenterHorizontally, // Centra los elementos horizontalmente
        verticalArrangement = Arrangement.Center // Centra los elementos verticalmente
    ) {
        Text(// Muestra un mensaje con el ganador o indica si hubo un empate
            if (
                ganador == "Empate"
            ) "Ha habido un empate" else "Ha ganado el $ganador",
            style = MaterialTheme.typography.titleLarge,// Aplica un estilo de título grande
            color = MaterialTheme.colorScheme.primary
        )// Usa el color principal del tema
        Spacer(Modifier.size(64.dp))// Espacio entre el texto y el botón
        Button( // Botón para reiniciar el juego
            onClick = {
                onRestart()// Llama a la función onRestart cuando se presiona
            },
            content = {
                Icon(// Icono de reinicio dentro del botón
                    imageVector = Icons.Default.Refresh,// Usa el icono de "Refresh"
                    contentDescription = "Reiniciar"// Descripción accesible del icono
                )
                Text("Reiniciar")// Texto del botón
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewGameOverScreen() {
    GameOverScreen(
        ganador = "1",
        onRestart = {  }
    )
}