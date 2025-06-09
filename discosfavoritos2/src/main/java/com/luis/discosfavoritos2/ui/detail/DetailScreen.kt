package com.luis.discosfavoritos2.ui.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.twotone.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.luis.discosfavoritos2.ui.AppViewModelProvider
import com.luis.discosfavoritos2.ui.add.DiscoDetails
import com.luis.discosfavoritos2.ui.components.ListaDiscosTopAppBar
import com.luis.discosfavoritos2.ui.navigation.NavigationDestination

object DetailDestination : NavigationDestination {
    override val route = "detail"
    override val title = "Detail"
    const val detailIdArg = "detailId"
    val routeWithArgs = "$route/{$detailIdArg}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    Scaffold(
        topBar = {
            ListaDiscosTopAppBar(
                title = DetailDestination.title,
                canNavigateBack = true,
                navigateBack = onNavigateBack
            )






        /*  Si nos pidiera reutilizar el mismo composable para la barra superior en todas las
        pantallas, pasandole el titulo cuando este cambia y un booleano para saber si tiene que
        mostrar el icono de navegar hacia la pantalla anterior




        CenterAlignedTopAppBar(
                title = { Text("Detail") },
                modifier = modifier,
                colors = TopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    scrolledContainerColor = MaterialTheme.colorScheme.primary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary,
                ),
                navigationIcon = {
                    IconButton(
                        onClick = onNavigateBack
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            //contentDescription = stringResource(string.back_button)
                            contentDescription = "Back"

                        )
                    }
                }
            )
 */






        },
    ){
        DetailsBody(
            discoDetails = viewModel.discoDetailUiState.discoDetails,
            modifier = modifier.padding(it),
        )
    }
}
@Composable
fun DetailsBody(discoDetails: DiscoDetails, modifier: Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        DiscoDetailsInformation(discoDetails = discoDetails)
    }
}

@Composable
fun DiscoDetailsInformation(discoDetails: DiscoDetails) {
    Column(modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
       ) {
        InfoRow(label = "Título", value = discoDetails.titulo)
        InfoRow(label = "Autor", value = discoDetails.autor)
        InfoRow(label = "Número de canciones", value = discoDetails.numCanciones)
        InfoRow(label = "Año de publicación", value = discoDetails.publicacion)
        RatingRow(label = "Valoración", selectedRating = discoDetails.valoracion.toInt())
    }
}

@Composable
fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = Modifier.weight(0.3f)) {
            Text(text = label,
                fontWeight = FontWeight.Bold)
        }
        Box(modifier = Modifier.weight(0.7f)) {
            Text(text = value,
                fontStyle = FontStyle.Italic
                )
        }
    }
}

@Composable
fun RatingRow(label: String, selectedRating: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = Modifier.weight(0.3f)) {
            Text(text = label,
                fontWeight = FontWeight.Bold)
        }
        Box(modifier = Modifier.weight(0.7f)) {
            Row {
                for (i in 1..5) {
                    Icon(
                        imageVector = if (i <= selectedRating) Icons.Filled.Star else Icons.TwoTone.Star,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.inversePrimary
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun DetailScreenPreview() {
    val fakeDiscoDetails = DiscoDetails(
        titulo = "Álbum Ejemplo",
        autor = "Artista Ficticio",
        numCanciones = "10",
        publicacion = "2023",
        valoracion = "4"
    )

    Scaffold(
        topBar = {
            ListaDiscosTopAppBar(
                title = DetailDestination.title,
                canNavigateBack = true,
                navigateBack = {} // acción vacía para preview
            )
        }
    ) { padding ->
        DetailsBody(
            discoDetails = fakeDiscoDetails,
            modifier = Modifier.padding(padding)
        )
    }
}
