package com.luis.triviaapproom.data  // Define el paquete donde se encuentra este archivo

import com.luis.triviaapproom.model.QuestionApi
import com.luis.triviaapproom.network.TriviaApiService

// Define una interfaz para el repositorio de preguntas
interface QuestionRepository {
    // Función suspendida que obtiene una lista de preguntas basadas en la cantidad y categoría
    suspend fun getQuestions(quantity: Int, category: Int): List<QuestionApi>


    //fun getLocalQuestions(quantity: Int, category: Int): List<Question> // Método para obtener preguntas locales

}

// Clase que implementa el repositorio de preguntas obtenidas desde la red
class NetworkQuestionsRepository(
    private val triviaApiService: TriviaApiService  // Se pasa una instancia del servicio de la API
) : QuestionRepository {
    // Implementación de la función getQuestions que obtiene las preguntas de la API
    override suspend fun getQuestions(quantity: Int, category: Int): List<QuestionApi> {
        // Realiza la llamada a la API utilizando los parámetros cantidad y categoría
        val response = triviaApiService.getApiQuestions(
            amount = quantity, // La cantidad de preguntas que quieres obtener
            category = category // El identificador de la categoría de preguntas
        )
        // Si la respuesta es exitosa, retorna las preguntas obtenidas, de lo contrario, retorna una lista vacía
        return if (response.isSuccessful) {
            response.body()?.results ?: emptyList()  // Si el cuerpo de la respuesta es válido, devuelve las preguntas, si no, lista vacía
        } else {
            emptyList()  // En caso de que la respuesta no sea exitosa, se devuelve una lista vacía
        }
    }
//    // Método para obtener preguntas locales
//    override fun getLocalQuestions(quantity: Int, category: Int): List<Question> {
//        return questions.values.shuffled().take(quantity) // Devuelve preguntas locales aleatorias
//    }
}
