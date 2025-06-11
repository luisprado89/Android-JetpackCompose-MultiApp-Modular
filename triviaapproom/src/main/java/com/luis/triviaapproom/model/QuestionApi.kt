package com.luis.triviaapproom.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Representa una pregunta obtenida desde la API.
 * La anotación @Serializable permite que esta clase sea serializada/deserializada con Kotlinx Serialization.
 * La función toQuestion() convierte el modelo de datos de la API en un objeto de la clase Question
 * que se usará en la aplicación.
 */
@Serializable
data class QuestionApi(
    @SerialName("question")
    val question: String,  // Pregunta obtenida de la API

    @SerialName("correct_answer")
    val correct_answer: String,  // Respuesta correcta de la API

    @SerialName("incorrect_answers")
    val incorrect_answers: List<String>  // Lista de respuestas incorrectas proporcionadas por la API

) {
    /**
     * Convierte el objeto QuestionApi en un objeto Question para su uso en la aplicación.
     *
     * @return Un objeto Question con la pregunta, opciones de respuesta mezcladas y la respuesta correcta.
     */
    fun toQuestion(): Question {
        val options = incorrect_answers.toMutableList()  // Convierte la lista inmutable de respuestas incorrectas en una mutable
        options.add(correct_answer)  // Agrega la respuesta correcta a la lista de opciones
        return Question(
            question,// Asigna la pregunta obtenida de la API a la propiedad 'question' del objeto 'Question'.
            options.shuffled(),  // Mezcla las opciones de respuesta (incluyendo la correcta) para que el orden sea aleatorio cada vez que se presenta la pregunta.
            correct_answer // Asigna la respuesta correcta (obtenida de la API) a la propiedad 'correctAnswer'
        )
    }
}
