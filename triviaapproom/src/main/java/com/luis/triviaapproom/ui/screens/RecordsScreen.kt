package com.luis.triviaapproom.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.luis.triviaapproom.data.Game
import kotlin.collections.forEach

// Composable principal que representa la pantalla de records
@Composable
fun RecordsScreen(
    recordsByCategory: List<Game>, // Lista de records agrupados por categoría
    allGames: List<Game>, // Lista de todos los juegos
    onBack: () -> Unit,// Acción para regresar
) {
    Column(// Contenedor principal que organiza los elementos de forma vertical
        // Column content alignment
        modifier = Modifier.fillMaxSize(),// Ocupa todo el tamaño disponible
        horizontalAlignment = Alignment.CenterHorizontally,// Alinea horizontalmente al centro
        verticalArrangement = Arrangement.Top// Alinea los elementos verticalmente desde la parte superior
    ) {
        // Records screen title Título de la pantalla
        Text("Records", style = MaterialTheme.typography.titleLarge)
        // Records by category Mostrar los records por categoría
        if (recordsByCategory.isEmpty()) {
            Text("No records found")// Si no hay records por categoría, muestra un mensaje
        } else {// Si hay records por categoría, muestra el título y la lista
            Text(
                "Records by category",
                style = MaterialTheme.typography.titleMedium
            )
            Column {// Para cada juego en recordsByCategory, se llama a CategoryRecord para mostrar la información
                recordsByCategory.forEach { game ->
                    CategoryRecord(game.category, game.name, game.score)
                }
            }

        }
        Spacer(modifier = Modifier.size(16.dp)) // Espacio entre las secciones
        if (allGames.isEmpty()) {// Mostrar todos los juegos
            Text("No records found") // Si no hay juegos, muestra un mensaje
        } else { // Si hay juegos, muestra el título y la lista de juegos
            Text("All games info", style = MaterialTheme.typography.titleMedium)
            LazyColumn { // LazyColumn es una lista que se carga de manera eficiente
                items(allGames) { game ->
                    GameInfo(game) // Muestra la información de cada juego
                }
            }
        }
        Spacer(modifier = Modifier.size(16.dp))// Espacio antes del botón
        // Back button
        Button(onClick = onBack) {
            Text("Back")// El texto del botón es "Back"
        }
    }
}
// Composable que muestra un record por categoría
@Composable
fun CategoryRecord(
    category: String,// Categoría del juego
    name: String,// Nombre del jugador
    record: Int,// Puntuación del jugador
) {
    Row(
//Fila
        horizontalArrangement = Arrangement.SpaceBetween, // Espaciado entre los elementos
        modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.secondary)
            .padding(4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(category, color = MaterialTheme.colorScheme.onSecondary)// Muestra la categoría
        Text(
            "Player: $name",
            color = MaterialTheme.colorScheme.onSecondary
        ) // Muestra el nombre del jugador
        Text(
            "Record: $record points",
            color = MaterialTheme.colorScheme.onSecondary
        )// Muestra el record del jugador
    }
}
// Composable que muestra la información de un juego
@Composable
fun GameInfo(
    game: Game,// El juego que contiene la información a mostrar
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,// Espaciado entre los elementos
        modifier = Modifier.padding(8.dp)
            .fillMaxWidth(),// Relleno entre elementos y asegura que ocupe todo el ancho
        verticalAlignment = Alignment.CenterVertically,// Alinea los elementos verticalmente al centro
    ) {
        Text(game.name)
        Text("${game.score} points in ${game.category}")// Muestra la puntuación y categoría del juego
    }
}