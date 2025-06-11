package com.luis.triviaapproom.data  // Define el paquete donde se encuentra esta interfaz y su implementación

import kotlinx.coroutines.flow.Flow  // Importa Flow para manejar datos de manera reactiva y asíncrona

// Interfaz que define las operaciones del repositorio para gestionar los juegos
interface GameRepository {
    fun getAllGames(): Flow<List<Game>>  // Obtiene la lista de todos los juegos en formato Flow
    fun getBestGame(category: String): Flow<Game>  // Obtiene el mejor juego de una categoría específica
    suspend fun insertGame(game: Game)  // Inserta un nuevo juego en la base de datos (operación suspendida)
}
// Implementación concreta de GameRepository que interactúa con GameDao
class GameRepositoryImpl(private val gameDao: GameDao) : GameRepository {
    // Llama a la función del DAO para obtener todos los juegos
    override fun getAllGames(): Flow<List<Game>> = gameDao.getAllGames()
    // Llama a la función del DAO para obtener el mejor juego de una categoría específica
    override fun getBestGame(category: String): Flow<Game> = gameDao.getBestGame(category)
    // Llama a la función del DAO para insertar un juego en la base de datos
    override suspend fun insertGame(game: Game) = gameDao.insertGame(game)
}
