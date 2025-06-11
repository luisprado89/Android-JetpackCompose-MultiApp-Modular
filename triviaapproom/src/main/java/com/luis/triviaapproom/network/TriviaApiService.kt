package com.luis.triviaapproom.network  // Define el paquete donde se encuentra este archivo

import com.luis.triviaapproom.model.QuestionApi  // Importa el modelo QuestionApi
import kotlinx.serialization.Serializable  // Importa la anotación Serializable de Kotlin para poder serializar los objetos
import retrofit2.Response  // Importa la clase Response de Retrofit para manejar la respuesta de la API
import retrofit2.http.GET  // Importa la anotación GET para indicar que se hará una petición GET
import retrofit2.http.Query  // Importa la anotación Query para agregar parámetros a la URL de la petición

/**
 * TriviaApiService para obtener las preguntas desde la API
 *
 * getApiQuestions() obtiene las preguntas desde la API con la cantidad y tipo especificados
 */
interface TriviaApiService {
    @GET("api.php")  // Define la URL del endpoint de la API que devuelve las preguntas
    suspend fun getApiQuestions(
        @Query("amount") amount: Int = 10,  // Parámetro que define la cantidad de preguntas a obtener, con un valor por defecto de 10
        @Query("type") type: String = "multiple",  // Parámetro que define el tipo de respuestas, en este caso "multiple" por defecto (preguntas de opción múltiple)
        @Query("category") category: Int  // Parámetro que define la categoría de las preguntas, que debe ser un valor entero (por ejemplo, 9 para General Knowledge)
    ): Response<QuestionApiResponse>  // La función retorna una respuesta de Retrofit que contiene un objeto de tipo QuestionApiResponse
}

@Serializable  // Marca esta clase para que sea serializable y pueda ser convertida a y desde JSON
data class QuestionApiResponse(
    val response_code: Int,  // Código de respuesta de la API (usualmente 0 si la petición fue exitosa)
    val results: List<QuestionApi>  // Lista de resultados que contiene las preguntas obtenidas de la API
)
