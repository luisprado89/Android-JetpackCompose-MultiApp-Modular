package com.luis.triviaapproom.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.luis.triviaapproom.model.Question
import androidx.lifecycle.viewmodel.compose.viewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameScreen(
    gameViewModel: GameViewModel = viewModel(factory = GameViewModel.Companion.Factory),
) {
    // Game view model  // Estado del ViewModel del juego
    val gameViewState by gameViewModel.gameViewState.collectAsState()
    Scaffold (
        topBar = {
            // Game top bar
            TopAppBar (
                title = { Text("Trivia App") }, // Título de la barra superior
                colors = TopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary, // Color de fondo de la barra
                    titleContentColor = MaterialTheme.colorScheme.onPrimary, // Color del texto del título
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary, // Color de los íconos de acción
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary, // Color del ícono de navegación
                    scrolledContainerColor = MaterialTheme.colorScheme.primary, // Color de fondo al desplazarse
                )
            )
        },
        bottomBar = {// Barra inferior del juego
            // Game bottom bar
            if (gameViewState.uiState == GameUiState.Success) { // Solo muestra la barra si el estado es de éxito
                BottomAppBar(
                    containerColor = MaterialTheme.colorScheme.primary, // Color de fondo de la barra inferior
                    contentColor = MaterialTheme.colorScheme.onPrimary, // Color del contenido de la barra inferior
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(16.dp), // Modificador para llenar el ancho y agregar padding
                        horizontalArrangement = Arrangement.SpaceBetween // Espacio entre los elementos
                    ) {// Muestra el índice de la pregunta actual y el total de preguntas
                        Text("Question: ${gameViewState.currentQuestionIndex + 1}/${gameViewState.numberOfQuestions}")
                        Text(// Muestra el porcentaje de respuestas correctas
                            "Correct Answers: ${gameViewState.currentPercentage} %",
                            fontWeight = FontWeight.Bold// Establece el texto en negrita
                        )
                    }
                }
            }
        }
    ) {
        // Game content
        Column (// Contenido del juego
            modifier = Modifier.padding(it).fillMaxSize(), // Aplica padding y llena el tamaño disponible
            horizontalAlignment = Alignment.CenterHorizontally, // Alinea los elementos horizontalmente al centro
            verticalArrangement = Arrangement.Center // Organiza los elementos verticalmente al centro

        ) {
            // Game UI // Interfaz de usuario del juego
            when (gameViewState.uiState) {
                GameUiState.Home -> {
                    // Home state
                    HomeScreen(
                        increseQuantity = { gameViewModel.increaseQuantity() },// Aumenta la cantidad de preguntas
                        decreaseQuantity = { gameViewModel.decreaseQuantity() },// Disminuye la cantidad de preguntas
                        quantity = gameViewState.numberOfQuestions, // Cantidad de preguntas
                        playerName = gameViewState.playerName, // Nombre del jugador
                        onChangePlayerName = { playerName ->
                            gameViewModel.onChangePlayerName(
                                playerName
                            )
                        },// Cambia el nombre del jugador
                        category = gameViewState.category,// Categoría seleccionada
                        onChangeCategory = { category -> gameViewModel.onChangeCategory(category) },// Cambia la categoría
                        categories = gameViewModel.listOfCategories(),// Lista de categorías
                        expanded = gameViewState.expanded, // Estado del menú desplegable
                        expandDropDownMenu = { expanded -> gameViewModel.expandDropDownMenu(expanded) }, // Expande o colapsa el menú
                        onStartGame = { playerName: String, quantity: Int, category: Category ->
                            gameViewModel.loadQuestions(
                                playerName,
                                quantity,
                                category
                            )// Inicia el juego
                        },
                        record = gameViewState.actualRecord, // Registro actual
                        onGoToRecords = { gameViewModel.onGoToRecords() }// Navega a los registros o record
                    )
                }//Fin de GameUiState.Home ->
                GameUiState.Records -> { // Estado de registros
                    // Records state
                    RecordsScreen(
                        recordsByCategory = gameViewState.recordsByCategory, // Registros por categoría
                        allGames = gameViewState.allGames, // Todos los juegos
                        onBack = { gameViewModel.onBackToHome() },// Regresa a la pantalla de inicio
                    )
                }// Fin de GameUiState.Records ->
                GameUiState.Loading -> { // Estado de carga
                    // Loading state
                    Text("Loading...") // Muestra un mensaje de carga
                } // Fin de GameUiState.Loading ->
                is GameUiState.Error -> {// Estado de error
                    // Error state
                    Text("Error: ${(gameViewState.uiState as GameUiState.Error).message}")// Muestra el mensaje de error
                } // Fin de GameUiState.Error ->
                GameUiState.Success -> {// Estado de juego cargado
                    // Game state
                    GameZone(
                        question = gameViewState.currentQuestion,// Pregunta actual
                        questionReplied = gameViewState.questionReplied,// Indica si la pregunta ha sido respondida
                        gameFinished = gameViewState.gameFinished, // Indica si el juego ha terminado
                        newRecord = gameViewState.newRecord,  // Indica si se ha establecido un nuevo récord
                        currentPercentage = gameViewState.currentPercentage,  // Porcentaje actual de respuestas correctas
                        onAnswerSelected = { answer: String -> gameViewModel.onAnswerSelected(answer) },  // Maneja la selección de respuesta
                        onNextQuestion = { gameViewModel.onNextQuestion()}, // Avanza a la siguiente pregunta
                        onRestartGame = { gameViewModel.onRestartGame() }, // Reinicia el juego
                        onBackToHome = { gameViewModel.onBackToHome() }, // Regresa a la pantalla de inicio
                        onGoToRecords = { gameViewModel.onGoToRecords() }, // Navega a los registros
                    )
                }
            }
        }
    }
}

@Composable
fun GameZone(
    question: Question?,
    questionReplied : Boolean,
    gameFinished: Boolean,
    newRecord: Boolean,
    currentPercentage: Int = 0,
    onAnswerSelected: (String) -> Unit,
    onNextQuestion: () -> Unit,
    onRestartGame: () -> Unit,
    onBackToHome: () -> Unit,
    onGoToRecords: () -> Unit,
) {
    // Game zone
    Column (
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Game question
        Text(question?.question ?: "No question found",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary)
        // Game options
        question?.options?.forEach { option ->
            Button(
                onClick = { onAnswerSelected(option) },
                modifier = Modifier.padding(8.dp)
                    .fillMaxWidth(),
                colors = ButtonColors(
                    contentColor = MaterialTheme.colorScheme.onSecondary,
                    containerColor = MaterialTheme.colorScheme.secondary,
                    disabledContentColor = MaterialTheme.colorScheme.onSecondary,
                    disabledContainerColor = (if (
                        questionReplied && option == question.correctAnswer
                    ) Color.Green else Color.Red).copy(alpha = 0.5f),
                ),
                enabled = questionReplied.not()
            ) {
                Text(option)
            }
        }
        if (gameFinished) {
            // Final score dialog
            FinalScoreDialog(
                score = currentPercentage,
                newRecord = newRecord,
                onRestartGame = onRestartGame,
                onBackToHome = { onBackToHome() },
                onGoToRecords = { onGoToRecords() },
            )
        } else {
            // Next button
            Button(
                onClick = { onNextQuestion() },
                modifier = Modifier.padding(8.dp)
                    .fillMaxWidth(),
                enabled = questionReplied,
                colors = ButtonColors(
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    containerColor = MaterialTheme.colorScheme.primary,
                    disabledContentColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.5f),
                    disabledContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                )
            ) {
                Text("Next")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FinalScoreDialog(
    score: Int,
    newRecord: Boolean,
    onRestartGame: () -> Unit,
    onBackToHome: () -> Unit,
    onGoToRecords: () -> Unit,
    modifier: Modifier = Modifier,
) {
    BasicAlertDialog(
        onDismissRequest = { onBackToHome() },
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surface),
        ) {

        Surface (
            modifier = Modifier.wrapContentWidth().wrapContentHeight(),
            shape = MaterialTheme.shapes.large,
            tonalElevation = AlertDialogDefaults.TonalElevation
        ) {
            Column(
                modifier = modifier.padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Text("Game finished!", style = MaterialTheme.typography.titleLarge)
                Text("Your score: $score %", style = MaterialTheme.typography.titleMedium)
                if (newRecord) {
                    Text(
                        "New record!",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.secondary,
                    )
                }
                TextButton(
                    onClick = { onGoToRecords() },
                    modifier = Modifier.padding(8.dp),
                ) {
                    Text("See Records")
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    TextButton(
                        onClick = { onRestartGame() },
                        modifier = Modifier.padding(8.dp),
                    ) {
                        Text("Try Again")
                    }
                    TextButton(
                        onClick = { onBackToHome() },
                        modifier = Modifier.padding(8.dp),
                    ) {
                        Text("Go to Home")
                    }

                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FinalScoreDialogPreview() {
    FinalScoreDialog(
        score = 100,
        newRecord = true,
        onRestartGame = {},
        onBackToHome = {},
        onGoToRecords = {},
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    GameScreen()
}
