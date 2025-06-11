package com.luis.triviaapproom.data  // Define el paquete donde se encuentra este archivo.

import android.content.Context  // Importa la clase Context de Android para acceder a recursos y servicios del sistema.
import com.luis.triviaapproom.network.TriviaApiService  // Importa el servicio que define las llamadas a la API.
import retrofit2.Retrofit  // Importa la clase Retrofit para realizar llamadas a servicios web RESTful.
import retrofit2.converter.gson.GsonConverterFactory  // Importa el convertidor Gson para convertir las respuestas JSON en objetos Kotlin.


/**
 * Interfaz que define los repositorios necesarios para la aplicación.
 * Contiene dos propiedades: uno para las preguntas y otro para los juegos.
 */
interface AppContainer {
    val questionRepository: QuestionRepository  // Repositorio para manejar las preguntas.
    val gameRepository: GameRepository  // Repositorio para manejar los datos del juego.
}

/**
 * Implementación concreta de AppContainer.
 * Proporciona acceso a las implementaciones de los repositorios de preguntas y juegos.
 */
class DefaultAppcontainer(context: Context): AppContainer {
    // URL base para la API que provee las preguntas de trivia.
    private val baseUrl = "https://opentdb.com/"

    // Inicializa el objeto Retrofit para realizar solicitudes HTTP.
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())  // Utiliza Gson para convertir las respuestas JSON en objetos Kotlin.
        .baseUrl(baseUrl)  // Define la URL base para las solicitudes.
        .build()  // Construye la instancia de Retrofit.
    // Crea el servicio de la API de Trivia usando Retrofit.
    private val retrofitService: TriviaApiService by lazy {
        retrofit.create(TriviaApiService::class.java) }
    // Inicializa el repositorio de preguntas usando el servicio de la API.
    override val questionRepository: QuestionRepository by lazy {
        NetworkQuestionsRepository(retrofitService) }
    // Aquí comienza la parte relacionada con Room.
    // Obtiene la base de datos de la aplicación.
    private val appDatabase = AppDatabase.getDatabase(context)

    // Obtiene el DAO para interactuar con la tabla de juegos en la base de datos.
    private val gameDao = appDatabase.gameDao()

    // Inicializa el repositorio de juegos utilizando el DAO.
    override val gameRepository: GameRepository by lazy { GameRepositoryImpl(gameDao) }
}
