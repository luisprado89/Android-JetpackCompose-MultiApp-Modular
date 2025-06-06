package com.luis.discosfavoritos2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.luis.discosfavoritos2.ui.theme.AndroidJetpackComposeMultiAppModularTheme

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
