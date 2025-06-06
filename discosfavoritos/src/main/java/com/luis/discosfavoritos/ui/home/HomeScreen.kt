package com.luis.discosfavoritos.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.twotone.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.luis.discosfavoritos.ListaDiscosTopAppBar
import com.luis.discosfavoritos.R
import com.luis.discosfavoritos.data.Disco
import com.luis.discosfavoritos.ui.AppViewModelProvider
import com.luis.discosfavoritos.ui.navigation.NavigationDestination

object HomeDestination : NavigationDestination {
    override val route = "home"
    override val title = "DiscosApp"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToAdd: () -> Unit,
    onNavigateToDetail: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val state by viewModel.homeUiState.collectAsState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            ListaDiscosTopAppBar(
                title = HomeDestination.title,
                canNavigateBack = false,
                scrollBehavior = scrollBehavior
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Row (
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    if (state.discoList.isEmpty()) {
                        Text(stringResource(R.string.no_discos))
                    } else {
                        Text(
                            stringResource(R.string.average_rating,state.valoracionMedia.toString()),
                            style = MaterialTheme.typography.titleLarge,)
                    }
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNavigateToAdd,
                shape = MaterialTheme.shapes.medium
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.disco_entry_title)
                )
            }
        }
    ) { innerPadding ->
        HomeBody(
            discos = state.discoList,
            onNavigateToDetail = onNavigateToDetail,
            insertarDiscosDePrueba = viewModel::insertarDiscosDePrueba,
            onShowOrHideDeleteDialog = viewModel::onShowOrHideDeleteDialog,
            modifier = Modifier.padding(innerPadding)
        )
        if (state.showDeleteDialog) {
            DeleteConfirmationDialog(
                title = state.discoToDelete?.titulo ?: "",
                onDeleteConfirm = {
                    viewModel.deleteDisco(state.discoToDelete!!)
                },
                onDismiss = {
                    viewModel.onShowOrHideDeleteDialog(false, state.discoToDelete!!)
                }
            )
        }
    }
}

@Composable
fun HomeBody(
    discos: List<Disco>,
    modifier: Modifier,
    insertarDiscosDePrueba: () -> Unit = {},
    onNavigateToDetail: (Int) -> Unit,
    onShowOrHideDeleteDialog: (Boolean, Disco) -> Unit
) {
    if (discos.isEmpty()){
        Column (
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(R.string.no_discos),
                style = MaterialTheme.typography.titleLarge
            )
            Icon(
                imageVector = Icons.Default.Clear,
                contentDescription = stringResource(R.string.disco_entry_title),
                modifier = Modifier.padding(16.dp).size(64.dp)
            )
            Button(
                onClick = insertarDiscosDePrueba,
            ) {
                Text(stringResource(R.string.insert_test_discos))
            }
        }
    } else {
        LazyColumn(
            modifier = modifier
        ) {
            items(discos) {
                DiscoListItem(
                    disco = it,
                    onNavigateToDetail = onNavigateToDetail,
                    onShowOrHideDeleteDialog = onShowOrHideDeleteDialog
                )
            }
        }
    }
}

@Composable
fun DiscoListItem(
    disco: Disco,
    onNavigateToDetail: (Int) -> Unit,
    onShowOrHideDeleteDialog: (Boolean, Disco) -> Unit
) {
    Row (
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.inversePrimary)
            .padding(8.dp)
            .clickable(
                onClick = { onNavigateToDetail(disco.id) }
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Column (
            modifier = Modifier.weight(0.4f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Text(text = disco.titulo,
                style = MaterialTheme.typography.titleMedium)
            Text(text = disco.autor,
                style = MaterialTheme.typography.titleSmall)
        }
        Row (
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            for (i in 1..5) {
                Icon(
                    imageVector = if (i <= disco.valoracion) Icons.Filled.Star else Icons.TwoTone.Star,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }

        IconButton(
            onClick = {
                onShowOrHideDeleteDialog(true, disco)
            }
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = stringResource(R.string.delete_button),
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun DeleteConfirmationDialog(
    title: String,
    onDeleteConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(R.string.delete_dialog_title)) },
        text = { Text(stringResource(R.string.delete_dialog_message, title)) },
        confirmButton = {
            TextButton(onClick = {
                onDeleteConfirm()
                onDismiss()
            }) {
                Text(stringResource(R.string.delete_dialog_confirm_button))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.delete_dialog_dismiss_button))
            }
        }
    )
}