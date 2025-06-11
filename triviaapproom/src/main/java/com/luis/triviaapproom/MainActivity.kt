package com.luis.triviaapproom

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.luis.triviaapproom.ui.theme.AndroidJetpackComposeMultiAppModularTheme
import com.luis.triviaapproom.ui.screens.GameScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidJetpackComposeMultiAppModularTheme {
                GameScreen()
            }
        }
    }
}
