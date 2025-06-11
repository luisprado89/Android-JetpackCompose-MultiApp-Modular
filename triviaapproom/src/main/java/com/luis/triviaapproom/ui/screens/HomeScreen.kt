package com.luis.triviaapproom.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

// Función Composable principal que representa la pantalla de inicio del juego
@Composable
fun HomeScreen(
    increseQuantity: () -> Unit, // Función para incrementar la cantidad de preguntas
    decreaseQuantity: () -> Unit, // Función para decrementar la cantidad de preguntas
    quantity: Int = 5, // Cantidad de preguntas, por defecto 5
    onStartGame: (String, Int, Category) -> Unit, // Función para iniciar el juego
    playerName: String, // Nombre del jugador
    onChangePlayerName: (String) -> Unit, // Función para cambiar el nombre del jugador
    category: Category, // Categoría seleccionada
    onChangeCategory: (String) -> Unit, // Función para cambiar la categoría
    categories: List<Category>, // Lista de categorías disponibles
    expanded: Boolean = false, // Estado de si el menú desplegable está expandido
    expandDropDownMenu: (Boolean) -> Unit, // Función para expandir o contraer el menú desplegable
    record: Int = 0, // Puntaje actual del jugador
    onGoToRecords: () -> Unit, // Función para ir a la pantalla de records
) {
    // Home screen content
    Column (// Contenedor principal de la pantalla (organiza los elementos de forma vertical)
        // Column content alignment
        modifier = Modifier.fillMaxSize(), // Asegura que ocupe todo el espacio disponible
        horizontalAlignment = Alignment.CenterHorizontally, // Alinea los elementos horizontalmente en el centro
        verticalArrangement = Arrangement.Center // Alinea los elementos verticalmente en el centro
    ) {
        // Home screen title Título de la pantalla
        Text("Trivia App", style = MaterialTheme.typography.titleLarge)
        Row ( // Fila para los controles de cantidad de preguntas
            horizontalArrangement = Arrangement.SpaceBetween, // Distribuye los elementos a los extremos
            verticalAlignment = Alignment.CenterVertically, // Centra los elementos verticalmente
            modifier = Modifier.padding(16.dp) // Añade un relleno de 16dp a la fila
        ){// Botón para decrementar la cantidad de preguntas
            IconButton( onClick = decreaseQuantity// Llama a la función para decrementar la cantidad de preguntas
                ) {
                Icon(// Icono de flecha hacia abajo
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = "Decrease questions"
                )
                 }
            Text("Questions: $quantity")// Texto que muestra la cantidad de preguntas
            IconButton(// Botón para incrementar la cantidad de preguntas
                onClick = increseQuantity // Llama a la función para incrementar la cantidad de preguntas
            ) {
                Icon(// Icono de flecha hacia arriba
                    imageVector = Icons.Default.KeyboardArrowUp,
                    contentDescription = "Increase questions"
                )
            }
        }//Row   Fin de la fila
        OutlinedTextField(// Campo de texto para ingresar el nombre del jugador
            label = { Text("Player Name") }, // Etiqueta del campo de texto
            value = playerName, // Valor del campo de texto (nombre del jugador)
            onValueChange = onChangePlayerName, // Función que se llama al cambiar el texto
        )
        Box(// Contenedor que se utiliza para el botón de selección de categoría
            modifier = Modifier.padding(top = 16.dp)// Añade un relleno superior de 16dp
        ) {
            TextButton(// Botón que permite expandir o contraer el menú desplegable de categorías
                onClick =  {expandDropDownMenu(!expanded) }// Alterna el estado del menú desplegable
            ) {
                Text(category.name)// Texto que muestra el nombre de la categoría seleccionada
                Icon(// Icono que cambia según si el menú está expandido o no
                    imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = "Expand dropdown menu"
                )
            }
            DropdownMenu(// Menú desplegable para seleccionar una categoría
                expanded = expanded,// Si el menú está expandido o no
                onDismissRequest = {
                    expandDropDownMenu(false)// Cierra el menú si el usuario hace clic fuera de él
                },
            ) { // Se recorre la lista de categorías y se muestra una opción para cada una
                categories.forEach { category ->
                    DropdownMenuItem(// Cada item del menú que cambia la categoría seleccionada
                        onClick = { onChangeCategory(category.name)// Cambia la categoría seleccionada
                        },
                        text = {
                            Text("Categoría: " + category.name) // Muestra el nombre de la categoría
                        }
                    )
                }
            }//DropdownMenu
        }//Box

        Button(// Botón para iniciar el juego
            onClick = {
                onStartGame(playerName, quantity, category)// Llama a la función para iniciar el juego con los parámetros actuales
            },
            modifier = Modifier.padding(top = 16.dp)// Añade un relleno superior de 16dp
        ) {
            Text("Start Game")// Texto que aparece en el botón
        }//Button
        Row {// Fila para mostrar el record actual del jugador
            Text("Actual record: $record %") // Texto que muestra el puntaje actual del jugador
        }
        TextButton(// Botón para ir a la pantalla de records
            onClick = { onGoToRecords() },// Llama a la función para ir a la pantalla de records
            modifier = Modifier.padding(8.dp),// Añade un relleno de 8dp al botón
        ) {
            Text("See All Records")// Texto que aparece en el botón
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        increseQuantity = {},
        decreaseQuantity = {},
        onStartGame = { _, _, _ -> },
        playerName = "Player",
        onChangePlayerName = {},
        category = Category(1, "Category 1"),
        onChangeCategory = {},
        categories = listOf(
            Category(1, "Category 1"),
            Category(2, "Category 2"),
            Category(3, "Category 3"),
        ),
        expanded = false,
        expandDropDownMenu = {},
        onGoToRecords = {},
    )
}