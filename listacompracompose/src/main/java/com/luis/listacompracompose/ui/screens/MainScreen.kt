package com.luis.listacompracompose.ui.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.luis.listacompracompose.ui.state.ShoppingListViewModel
import kotlin.text.isBlank


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(shoppingListViewModel: ShoppingListViewModel = viewModel()) {
    val shoppingListUiState by shoppingListViewModel.uiState.collectAsState()
    Scaffold (
        topBar = {
            TopAppBar(
                title = {
                    Text( text = "Shopping List", fontSize = 24.sp)
                     },
                actions = {
                    OutlinedIconButton (
                        onClick = { shoppingListViewModel.deleteAllChecked() },
                        enabled = shoppingListUiState.isSomethingChecked,
                        ) {
                        Icon(imageVector = Icons.Filled.Delete, contentDescription = "Delete item")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary,
                ),
            )
        }
    ) {
        Column (
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            AddProductRow(
                value = shoppingListUiState.newProduct,
                changingStringValue = {
                    shoppingListViewModel.changingNewProduct(it)
                },
                addProduct = {
                    shoppingListViewModel.add(it)
                })
            LazyColumn(

            )
            {
                items(items = shoppingListUiState.list) { product ->
                    ShoppingListItem(
                        product.name,
                        product.checked,
                        remove = { shoppingListViewModel.remove(product) },
                        toggleChecked = { shoppingListViewModel.toggleChecked(product) },
                    )
                }
            }
        }
    }
}

@Composable
fun AddProductRow(
    value: String,
    context: Context = LocalContext.current,
    changingStringValue: (String) -> Unit,
    addProduct: (String) -> Boolean,
) {
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround,
    ){
        TextField(
            value = value,
            onValueChange = { changingStringValue(it) },
            singleLine = true,
            )
        IconButton(onClick = {
            if (value.isBlank()) {
                Toast.makeText(context, "Product name cannot be empty", Toast.LENGTH_SHORT).show()
                return@IconButton
            }
            if(!addProduct(value)) {
                Toast.makeText(context, "Product already exists", Toast.LENGTH_SHORT).show()
            }
            changingStringValue("")
        } ) {
            Icon(imageVector = Icons.Filled.Add, contentDescription = "Add item")
        }
    }
}


@Composable
fun ShoppingListItem(
    name: String,
    checked: Boolean,
    remove: () -> Unit,
    toggleChecked: (Boolean) -> Unit
) {
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp)
            .background(color = if (checked) Color.LightGray else Color.Transparent),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    )
    {
        Text(text = name)
        Spacer(modifier = Modifier.width(30.dp))
        Row {
            Checkbox(checked = checked, onCheckedChange = toggleChecked)
            IconButton(onClick = remove ) {
                Icon(imageVector = Icons.Filled.Delete, contentDescription = "Delete item")
            }
        }
    }
}