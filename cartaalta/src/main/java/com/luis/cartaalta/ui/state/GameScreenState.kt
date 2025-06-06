package com.luis.cartaalta.ui.state

// Data class que representa el estado de la pantalla del juego (GameScreen).
data class GameScreenState (
    // Indica si el jugador 1 ha tirado su carta (valor predeterminado es 'false').
    val jugador1HaTirado: Boolean = false,

    // Indica si el jugador 2 ha tirado su carta (valor predeterminado es 'false').
    val jugador2HaTirado: Boolean = false,

    // Indica si ambos jugadores han tirado sus cartas (valor predeterminado es 'false').
    val todosHanTirado: Boolean = false,

    // La carta tirada por el jugador 1, representada como un número entero (valor predeterminado es 0).
    val jugador1Carta: Int = 0,

    // La carta tirada por el jugador 2, representada como un número entero (valor predeterminado es 0).
    val jugador2Carta: Int = 0,

    // El nombre del ganador (valor predeterminado es una cadena vacía, lo que indica que aún no hay ganador).
    val ganador: String = ""
)
