package com.luis.discosfavoritos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.luis.discosfavoritos.ui.theme.AndroidJetpackComposeMultiAppModularTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidJetpackComposeMultiAppModularTheme {
                ListaDiscosApp()
            }
        }
    }
}
