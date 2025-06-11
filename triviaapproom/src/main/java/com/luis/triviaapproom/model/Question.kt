package com.luis.triviaapproom.model

import android.text.Html

// Define el paquete donde se encuentra esta clase

//import com.luisdpc.triviaapp_master.data.questions  // Importa la lista de preguntas desde el paquete de datos

/**
 * Representa una pregunta en el cuestionario.
 * Contiene el enunciado de la pregunta, las opciones de respuesta y la respuesta correcta.
 */
data class Question(
    private val rawQuestion: String,   // Pregunta sin procesar (puede contener entidades HTML)
    private val rawOptions: List<String>,  // Lista de opciones de respuesta sin procesar
    private val rawCorrectAnswer: String,  // Respuesta correcta sin procesar
) {
    // Propiedad que devuelve la pregunta con los caracteres especiales HTML decodificados
    val question: String
        get() = rawQuestion.decodeHtml()

    // Propiedad que devuelve la lista de opciones con caracteres especiales HTML decodificados
    val options: List<String>
        get() = rawOptions.map { it.decodeHtml() }

    // Propiedad que devuelve la respuesta correcta con caracteres especiales HTML decodificados
    val correctAnswer: String
        get() = rawCorrectAnswer.decodeHtml()

    /**
     * Método que valida si la respuesta ingresada por el usuario es correcta.
     * @param answer Respuesta ingresada por el usuario.
     * @return `true` si la respuesta es correcta, `false` en caso contrario.
     */
    fun validateAnswer(answer: String): Boolean {
        return answer == correctAnswer
    }
}

/**
 * Extensión de la clase String para decodificar caracteres HTML en texto plano.
 */
private fun String.decodeHtml(): String {
    return Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY).toString()
}

/**
 * Función que devuelve una lista de preguntas aleatorias.
 * @param numberOfQuestions Número de preguntas que se desean obtener.
 * @return Lista de preguntas seleccionadas aleatoriamente.
 */
//fun getQuestions(numberOfQuestions: Int): List<Question> {
//    return questions
//        .shuffled()  // Mezcla aleatoriamente las preguntas disponibles
//        .take(numberOfQuestions)  // Toma las primeras 'numberOfQuestions' preguntas de la lista mezclada
//}
