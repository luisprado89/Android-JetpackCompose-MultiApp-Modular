package com.luis.triviaapproom.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.luis.triviaapproom.TriviaApplication
import com.luis.triviaapproom.data.Game
import com.luis.triviaapproom.data.GameRepository
import com.luis.triviaapproom.data.QuestionRepository
import com.luis.triviaapproom.data.TriviaPreferencesRepository
//import com.luisdpc.triviaapp_master.data.questions



import com.luis.triviaapproom.model.Question
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

// Interfaz sellada que define los diferentes estados de la UI del juego
sealed interface GameUiState {
    data object Loading : GameUiState  // Estado de carga
    data class Error(val message: String) : GameUiState // Estado de error con un mensaje
    data object Success : GameUiState  // Estado de éxito
    data object Home : GameUiState  // Estado en la pantalla de inicio
    data object Records : GameUiState // Estado en la pantalla de récords
}

data class GameViewState(
    val uiState: GameUiState = GameUiState.Loading, // Estado actual de la UI
    val recordsByCategory: List<Game> = emptyList(), // Lista de récords filtrados por categoría
    val allGames: List<Game> = emptyList(), // Lista con todas las partidas registradas
    val questions: List<Question> = emptyList(), // Lista de preguntas cargadas en la partida
    val playerName: String = "", // Nombre del jugador
    val expanded: Boolean = false, // Indica si el menú desplegable de categorías está abierto
    val category: Category = categories.first(), // Categoría seleccionada (por defecto la primera de la lista)
    val currentQuestionIndex: Int = 0, // Índice de la pregunta actual
    val correctAnswers: Int = 0, // Número de respuestas correctas en la partida
    val numberOfQuestions: Int = 5, // Número total de preguntas en la partida
    val questionReplied: Boolean = false, // Indica si la pregunta actual ha sido respondida
    val currentQuestion: Question? = null, // Pregunta actual (nula al inicio)
    val currentPercentage: Int = 0, // Porcentaje de respuestas correctas
    val actualRecord: Int = 0, // Récord actual del usuario almacenado en preferencias
    val gameFinished: Boolean = false, // Indica si la partida ha finalizado
    val newRecord: Boolean = false, // Indica si el usuario ha logrado un nuevo récord
)
// Clase de datos para representar una categoría de preguntas
data class Category(
    val id: Int, // Identificador de la categoría
    val name: String, // Nombre de la categoría
)
// Lista de categorías disponibles
val categories = listOf(
    Category(10, "Books"),
    Category(11, "Film"),
    Category(12, "Music"),
    Category(13, "Musicals"),
    Category(14, "Television"),
    Category(15, "Video Games"),
    Category(16, "Board Games"),// Categoría de juegos de mesa
)
// ViewModel que maneja la lógica del juego
class GameViewModel(
    private val questionRepository: QuestionRepository, // Repositorio para obtener preguntas
    private val triviaPreferencesRepository: TriviaPreferencesRepository, // Repositorio de preferencias del usuario
    private val gameRepository: GameRepository, // Repositorio de juegos almacenados
) : ViewModel() {
    // Estado mutable del juego, que se actualizará conforme avanza la partida
    private val _gameViewState = MutableStateFlow(GameViewState())// Estado mutable del juego
    val gameViewState: StateFlow<GameViewState> = _gameViewState.asStateFlow() // Estado expuesto a la UI

    // Bloque de inicialización del ViewModel
    init {
        // Cargar el récord almacenado en las preferencias
        viewModelScope.launch {
            val record = triviaPreferencesRepository.recordFlow.first() // Obtiene el récord almacenado
            _gameViewState.value = _gameViewState.value.copy(actualRecord = record) // Lo asigna al estado
        }
        // Set the initial state to Home
        _gameViewState.value = _gameViewState.value.copy(uiState = GameUiState.Home) // Establece el estado inicial
    }
    // Función que carga preguntas para iniciar el juego
    fun loadQuestions(playerName: String, quantity: Int, category: Category) {
        viewModelScope.launch {
            _gameViewState.value = _gameViewState.value.copy(uiState = GameUiState.Loading) // Cambia el estado a Loading
            // Obtiene las preguntas del repositorio

                val questions = questionRepository.getQuestions(quantity, category.id)
                    .map { it.toQuestion() } // Mapea las preguntas a su modelo
                // Actualiza el estado del juego con las preguntas y otros datos
                _gameViewState.value = _gameViewState.value.copy(
                    uiState = GameUiState.Success, // Cambia el estado a Success
                    numberOfQuestions = quantity, // Establece la cantidad de preguntas
                    questions = questions, // Asigna las preguntas cargadas
                    playerName = playerName, // Asigna el nombre del jugador
                    category = category, // Asigna la categoría seleccionada
                    currentQuestionIndex = 0, // Reinicia el índice de la pregunta actual
                    correctAnswers = 0, // Reinicia el contador de respuestas correctas
                    questionReplied = false, // Indica que no se ha respondido ninguna pregunta
                    currentPercentage = 0, // Reinicia el porcentaje de respuestas correctas
                    gameFinished = false, // Indica que el juego no ha terminado
                    newRecord = false, // Indica que no hay un nuevo récord
                    currentQuestion = questions.firstOrNull(), // Asigna la primera pregunta o nula si no hay preguntas

                )
        }
    }
    // Función que se ejecuta cuando el usuario selecciona una respuesta
    fun onAnswerSelected(answer: String) {
        viewModelScope.launch {
            val currentQuestion = _gameViewState.value.questions.getOrNull(_gameViewState.value.currentQuestionIndex)
            if (currentQuestion != null) {// Verifica que la pregunta no sea nula
                // Calcula el número de respuestas correctas
                val correctAnswers = _gameViewState.value.correctAnswers +
                        if (currentQuestion.validateAnswer( answer)
                             ) 1 else 0// Actualiza el estado con el nuevo número de respuestas correctas y el porcentaje
                _gameViewState.value = _gameViewState.value.copy(
                    correctAnswers = correctAnswers, // Actualiza el número de respuestas correctas
                    currentPercentage = correctAnswers * 100 / (_gameViewState.value.currentQuestionIndex + 1), // Calcula el porcentaje
                    questionReplied = true,// Indica que se ha respondido la pregunta
                )
            }
        }
    }
    // Función que avanza a la siguiente pregunta
    fun onNextQuestion() {
        viewModelScope.launch {
            val currentQuestionIndex = _gameViewState.value.currentQuestionIndex + 1 // Incrementa el índice de la pregunta actual
            if (currentQuestionIndex < _gameViewState.value.numberOfQuestions) {// Verifica si hay más preguntas
                // Actualiza el estado con la nueva pregunta
                _gameViewState.value = _gameViewState.value.copy(
                    currentQuestionIndex = currentQuestionIndex,
                    currentQuestion = _gameViewState.value.questions.getOrNull(currentQuestionIndex), // Asigna la nueva pregunta
                    questionReplied = false, // Indica que la nueva pregunta no ha sido respondida
                )
            } else { // Si no hay más preguntas
                _gameViewState.value = _gameViewState.value.copy(gameFinished = true) // Indica que el juego ha terminado
                val game = Game(// Crea un objeto Game con los datos del jugador y su puntuación
                    name = _gameViewState.value.playerName,// Nombre del jugador
                    score = _gameViewState.value.correctAnswers,// Puntuación del jugador
                    category = _gameViewState.value.category.name,// Nombre de la categoría
                )
                gameRepository.insertGame(game)// Guarda la partida en la base de datos
                // Verifica si el porcentaje actual es mayor que el récord
                if (_gameViewState.value.currentPercentage > _gameViewState.value.actualRecord) {
                    _gameViewState.value = _gameViewState.value.copy(// Actualiza el récord actual y marca que hay un nuevo récord
                        actualRecord = _gameViewState.value.currentPercentage, // Actualiza el récord
                        newRecord = true,// Marca que hay un nuevo récord
                    )
                    triviaPreferencesRepository.writeTriviaPreferences(_gameViewState.value.currentPercentage)// Guarda el nuevo récord en preferencias
                }
            }
        }
    }
    // Reinicia el juego
    fun onRestartGame() {// Vuelve a cargar las preguntas
        loadQuestions(
            _gameViewState.value.playerName,
            _gameViewState.value.numberOfQuestions,
            _gameViewState.value.category)
    }
    // Disminuye la cantidad de preguntas
    fun decreaseQuantity() {
        if (_gameViewState.value.numberOfQuestions > 5)// Verifica que la cantidad de preguntas sea mayor a 5
            _gameViewState.value = _gameViewState.value.copy(
                numberOfQuestions = _gameViewState.value.numberOfQuestions - 1)// Disminuye la cantidad de preguntas
    }
    // Aumenta la cantidad de preguntas
    fun increaseQuantity() {
        if(_gameViewState.value.numberOfQuestions < 20) // Verifica que la cantidad de preguntas sea menor a 20
            _gameViewState.value = _gameViewState.value.copy(
                numberOfQuestions = _gameViewState.value.numberOfQuestions + 1)// Aumenta la cantidad de preguntas
    }
    // Regresa a la pantalla de inicio
    fun onBackToHome() {
        _gameViewState.value = _gameViewState.value.copy(uiState = GameUiState.Home)// Cambia el estado a Home
    }
    // Devuelve la lista de categorías
    fun listOfCategories() = categories// Retorna la lista de categorías
    // Cambia el nombre del jugador
    fun onChangePlayerName(playerName: String) {
        _gameViewState.value = _gameViewState.value.copy(playerName = playerName)// Actualiza el nombre del jugador en el estado
    }
    // Cambia la categoría seleccionada
    fun onChangeCategory(category: String) {
        _gameViewState.value = _gameViewState.value.copy(
            category = categories.first { it.name == category },
            expanded = false,// Cierra el menú desplegable
        )
    }
    // Expande o colapsa el menú desplegable
    fun expandDropDownMenu(expanded: Boolean) {
        _gameViewState.value = _gameViewState.value.copy(
            expanded = expanded)// Actualiza el estado del menú desplegable
    }
    // Carga los récords cuando el usuario navega a la pantalla de récords
    fun onGoToRecords() {
        viewModelScope.launch {
            loadRecords()// Llama a la función para cargar los récords
        }
    }
    // Función privada que carga los récords
    private suspend fun loadRecords() {
        // Carga los mejores juegos por categoría
        val bestGames = categories.map { category ->
            gameRepository.getBestGame(category.name).firstOrNull() ?: Game(
                name = "No player",// Nombre por defecto si no hay récord
                score = 0,// Puntuación por defecto
                category = category.name,// Nombre de la categoría
            )
        }// Actualiza el estado con los récords y todos los juegos
        _gameViewState.value = _gameViewState.value.copy(
            uiState = GameUiState.Loading, // Cambia el estado a Loading
            allGames = gameRepository.getAllGames().firstOrNull() ?: emptyList(),// Carga todos los juegos
            recordsByCategory = bestGames,  // Asigna los mejores juegos por categoría
        )// Cambia el estado a Records
        _gameViewState.value = _gameViewState.value.copy(
            uiState = GameUiState.Records,// Cambia el estado a Records
        )
    }


    // ViewModel Factory
    /*
    Explicación de la función viewModelFactory:
    - Esta función crea un ViewModelProvider.Factory que inicializa un GameViewModel.
    - La función initializer se encarga de inicializar el GameViewModel con el QuestionRepository.
    - La función viewModelFactory recibe un lambda que retorna un ViewModelProvider.Factory.
    - El lambda recibe un mapa de parámetros y retorna un ViewModel.
    - En este caso, el lambda recibe un mapa con un parámetro APPLICATION_KEY que es un TriviaApplication.
    - El ViewModel se inicializa con el QuestionRepository obtenido del TriviaApplication.
    Este companion object es necesario para poder crear un ViewModelProvider.Factory que inicialice un GameViewModel.
    La palabra reservada companion object permite definir un objeto que es parte de la clase GameViewModel.
     */
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {// Obtiene la aplicación desde el contexto
                val application = (this[APPLICATION_KEY] as TriviaApplication)
                // Obtiene el repositorio de preguntas desde la aplicación
                val questionRepository = application.container.questionRepository
                // Obtiene el repositorio de juegos desde la aplicación
                val gameRepository = application.container.gameRepository
                // Obtiene el repositorio de preferencias desde la aplicación
                val triviaPreferencesRepository = application.triviaPreferencesRepository
                GameViewModel(// Inicializa el GameViewModel con los repositorios
                    questionRepository = questionRepository,
                    gameRepository = gameRepository,
                     triviaPreferencesRepository = triviaPreferencesRepository)
            }
        }
    }
}