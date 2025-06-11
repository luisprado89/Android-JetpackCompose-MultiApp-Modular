package com.luis.triviaapproom.data  // Define el paquete donde se encuentra esta interfaz

import androidx.room.Dao  // Importa la anotación @Dao para definir una interfaz de acceso a datos en Room
import androidx.room.Insert  // Importa la anotación @Insert para insertar datos en la base de datos
import androidx.room.Query  // Importa la anotación @Query para ejecutar consultas SQL en la base de datos
import kotlinx.coroutines.flow.Flow  // Importa Flow para manejar datos de manera reactiva y asíncrona

// Define una interfaz DAO (Data Access Object) para interactuar con la tabla "game"
@Dao
interface GameDao {
    // Consulta todos los registros de la tabla "game" y devuelve un Flow con la lista de juegos
    @Query("SELECT * FROM game")
    fun getAllGames(): Flow<List<Game>>
    // Obtiene el juego con el mejor puntaje de una categoría específica, ordenado de mayor a menor
    @Query("SELECT * FROM game WHERE category= :category ORDER BY score DESC LIMIT 1")
    fun getBestGame(category: String): Flow<Game>
    @Insert// Inserta un nuevo registro en la tabla "game"
    suspend fun insertGame(game: Game)  // La función es suspend para ejecutarse en una corrutina
}
