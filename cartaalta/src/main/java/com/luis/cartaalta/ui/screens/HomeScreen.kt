package com.luis.cartaalta.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun HomeScreen(// Composable que representa la pantalla principal de la aplicación.
    onPlay: () -> Unit// Parámetro 'onPlay' es una función que se ejecutará cuando el usuario haga clic en el botón "Jugar".
) {
    Column (// Column se utiliza para organizar los elementos de la interfaz de usuario en una columna.
        modifier = Modifier.fillMaxSize(), // Modificador que hace que la columna ocupe todo el tamaño de la pantalla.
        verticalArrangement = Arrangement.Center, // Centra los elementos verticalmente.
        horizontalAlignment = Alignment.CenterHorizontally // Centra los elementos horizontalmente.
    ) {
        Text("Bienvenido al juego de Carta Alta",// Primer Text muestra un mensaje de bienvenida en el centro de la pantalla.
            style = MaterialTheme.typography.titleLarge, // Estilo de texto, se utiliza un estilo grande.
            color = MaterialTheme.colorScheme.primary) // Color primario del tema actual para el texto.
        Text("Pulsa en 'Jugar' para comenzar",// Segundo Text da instrucciones al usuario sobre cómo iniciar el juego.
            style = MaterialTheme.typography.bodyLarge, // Estilo de texto grande para cuerpo de la interfaz.
            color = MaterialTheme.colorScheme.primary) // Color primario para el texto.
        Button(// Botón que, al ser presionado, ejecuta la función 'onPlay' proporcionada.
            onClick = {
                onPlay()// Ejecuta la función 'onPlay' cuando se hace clic en el botón.
            },
            content = {// Dentro del botón, se coloca un texto y un icono.
                Text("Jugar")
                Icon(
                    imageVector = Icons.Default.PlayArrow,// Icono de un triángulo (icono de "Play").
                    contentDescription = "Jugar"
                )
            }
        )
    }
}
// Preview para la pantalla principal (HomeScreen)
@Preview(showBackground = true)
@Composable
fun HomeScreenPreview(){
    Surface (
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ){
        // Se pasa una función lambda vacía como parámetro onPlay (solo para fines de vista previa)
        HomeScreen(onPlay = { /* Acción de juego, vacía para la vista previa */ })
    }
}