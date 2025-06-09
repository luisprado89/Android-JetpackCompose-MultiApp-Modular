package com.luis.listacompraexamenbd

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.luis.listacompraexamenbd.ui.theme.AndroidJetpackComposeMultiAppModularTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidJetpackComposeMultiAppModularTheme {
                ListaCompraApp()
            }
        }
    }
}