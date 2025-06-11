package com.luis.triviaapproom.data  // Define el paquete donde se encuentra esta clase

import androidx.room.Entity  // Importa la anotación @Entity para definir una tabla en Room
import androidx.room.PrimaryKey  // Importa la anotación @PrimaryKey para establecer una clave primaria en la tabla

// Define una entidad de base de datos llamada "game"
@Entity(tableName = "game")
data class Game(
    @PrimaryKey(autoGenerate = true)  // Define la clave primaria de la tabla y permite que se genere automáticamente
    val id: Int = 0,  // Identificador único del juego, comienza en 0 por defecto

    val name: String,  // Nombre del jugador o de la partida
    val score: Int,  // Puntaje obtenido en el juego
    val category: String  // Categoría del juego o trivia
)
