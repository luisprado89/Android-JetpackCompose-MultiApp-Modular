package com.luis.cartaalta.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.luis.cartaalta.ui.state.GameScreenViewModel

// Función composable que representa la pantalla del juego
@Composable
fun GameScreen (
    onBackToMenu: () -> Unit, // Función para volver al menú principal
    onGameOver: (String) -> Unit, // Función que se ejecuta cuando el juego termina
) {
    GameScreenContent(// Llama a otra función composable que implementa la UI del juego
        onBackToMenu = onBackToMenu,
        onGameOver = onGameOver,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameScreenContent(
    onBackToMenu: () -> Unit, // Función para volver al menú principal
    onGameOver: (String) -> Unit, // Función que maneja el fin de la partida
    gameScreenVM: GameScreenViewModel = viewModel() // ViewModel que maneja el estado del juego

) {
    val state by gameScreenVM.state.collectAsState()// Obtiene el estado del juego como un State
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Carta Alta") },// Título en la barra superior
                navigationIcon = { // Botón de navegación para volver al menú principal
                    IconButton(
                        onClick = onBackToMenu
                    ) {
                        Icon(
                            Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = "Back to home"
                        )
                    }
                },
                colors = TopAppBarColors(
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    containerColor = MaterialTheme.colorScheme.primary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    scrolledContainerColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)// Aplica padding según la barra superior
                .fillMaxSize(),// Ocupa todo el tamaño disponible
            horizontalAlignment = Alignment.CenterHorizontally, // Centra horizontalmente
            verticalArrangement = Arrangement.Center // Centra verticalmente
        ) {
            PlayerCard(
// Representa la carta y botón de acción para el Jugador 1
                playerName = "Jugador 1",
                haTirado = state.jugador1HaTirado,// Indica si el jugador ya ha tirado
                carta = state.jugador1Carta.toString(),// Muestra la carta del jugador
                onTirar = { gameScreenVM.jugador1Tira() },// Acción cuando el jugador tira la carta
            )
            PlayerCard(// Representa la carta y botón de acción para el Jugador 2
                playerName = "Jugador 2",
                haTirado = state.jugador2HaTirado,// Indica si el jugador ya ha tirado
                carta = state.jugador2Carta.toString(),// Muestra la carta del jugador
                onTirar = { gameScreenVM.jugador2Tira() }// Acción cuando el jugador tira la carta
            )
            Button( // Botón para finalizar la partida cuando ambos jugadores han tirado
                onClick = {
                    onGameOver(state.ganador)// Llama a la función de fin de juego con el ganador
                },
                enabled = gameScreenVM.todosHanTirado(),// Habilitado solo si ambos han jugado
                content = { Text("Terminar partida") }
            )
        }
    }


}

@Composable
fun PlayerCard(// Composable que representa la interfaz de cada jugador
    playerName: String,// Nombre del jugador
    haTirado: Boolean = false, // Indica si el jugador ha tirado su carta
    carta: String = "", // Representación de la carta
    onTirar: () -> Unit // Acción cuando el jugador tira la carta
    ) {
    Row(
        verticalAlignment = Alignment.CenterVertically, // Alineación vertical centrada
        horizontalArrangement = Arrangement.Center // Distribución centrada
    ) {
        Button(
            onClick = onTirar,
            enabled = !haTirado,// Solo habilitado si el jugador no ha tirado aún
            content = { Text("Area de juego de $playerName") }// Muestra el nombre del jugador
        )
        Spacer(Modifier.size(8.dp))// Espacio entre el botón y el texto
        Text(
            text = if (haTirado) "Su carta es: $carta" else "No ha robado aún",// Muestra el estado del jugador
            modifier = Modifier.padding(start = 8.dp)// Aplica padding al texto
        )
    }
}
@Preview(showBackground = true)
@Composable
fun PreviewGameScreen() {
    GameScreen(
        onBackToMenu = { /* Acción de regreso al menú */ },
        onGameOver = {    }
    )
}