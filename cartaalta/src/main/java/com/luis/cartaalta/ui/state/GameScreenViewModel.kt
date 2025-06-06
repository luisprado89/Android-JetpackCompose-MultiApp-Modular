package com.luis.cartaalta.ui.state

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

// ViewModel que gestiona el estado de la pantalla del juego.
class GameScreenViewModel : ViewModel() {
    val _state = MutableStateFlow(GameScreenState())// _state es un MutableStateFlow que almacena el estado del juego.
    // state es un StateFlow que expone el estado del juego de manera inmutable.
    // Los observadores solo pueden leer el estado, no modificarlo directamente.
    val state : StateFlow<GameScreenState> = _state.asStateFlow()//state es una versión de solo lectura de _state, gracias a asStateFlow().
    //Esto impide que otras clases puedan modificar _state directamente, asegurando que solo el ViewModel pueda cambiarlo.
    //Variable      Tipo                            Modificable                 Proposito
    //_state	MutableStateFlow<GameScreenState>	✅ Sí (por el ViewModel)	Almacena y actualiza el estado del juego
    //state	    StateFlow<GameScreenState>	        ❌ No (solo lectura)	    Permite que la UI observe el estado sin modificarlo


    fun jugador1Tira() {// Función que simula la acción del jugador 1 al tirar su carta.
        _state.value = _state.value.copy(jugador1Carta = (1..2).random()) // Se actualiza el estado con una carta aleatoria para el jugador 1.
        _state.value = _state.value.copy(jugador1HaTirado = true)// Se marca que el jugador 1 ha tirado su carta.
        ganadorEs()// Se evalúa si hay un ganador después de que el jugador 1 tire su carta.
    }

    fun jugador2Tira() {// Función que simula la acción del jugador 2 al tirar su carta.
        _state.value = _state.value.copy(jugador2Carta = (1..2).random())// Se actualiza el estado con una carta aleatoria para el jugador 2.
        _state.value = _state.value.copy(jugador2HaTirado = true) // Se marca que el jugador 2 ha tirado su carta.
        ganadorEs()// Se evalúa si hay un ganador después de que el jugador 2 tire su carta.
    }
    // Función que verifica si ambos jugadores han tirado sus cartas.
    fun todosHanTirado() = _state.value.jugador1HaTirado && _state.value.jugador2HaTirado

    private fun ganadorEs() {// Función privada que determina el ganador del juego después de que ambos jugadores tiren su carta.
        if (todosHanTirado()){// Si ambos jugadores han tirado sus cartas, se evalúa el ganador.
            if (_state.value.jugador1Carta == _state.value.jugador2Carta) {// Si las cartas de ambos jugadores son iguales, es un empate.
                _state.value = _state.value.copy(ganador = "Empate")
                return
            }// Si las cartas no son iguales, se determina al ganador comparando las cartas.
            val jugador = if (_state.value.jugador1Carta > _state.value.jugador2Carta) 1 else 2
            _state.value = _state.value.copy(ganador = "Jugador $jugador")// Se actualiza el estado con el nombre del jugador ganador.
        }
    }
}