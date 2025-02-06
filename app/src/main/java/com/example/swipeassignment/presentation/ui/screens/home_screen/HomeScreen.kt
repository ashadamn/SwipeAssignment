package com.example.swipeassignment.presentation.ui.screens.home_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.swipeassignment.data.model.product_list_response.ProductListResponseDTO
import com.example.swipeassignment.domain.util.ApiResult
import com.example.swipeassignment.presentation.theme.Spacing
import com.example.swipeassignment.presentation.ui.screens.home_screen.components.ProductItem
import com.example.swipeassignment.presentation.util.UiStateHandler

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    state: ApiResult<List<ProductListResponseDTO>>,
    onFabClick: () -> Unit
) {

    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }
    var isSearchVisible by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    var previousProductList by remember { mutableStateOf<List<ProductListResponseDTO>>(emptyList()) }

    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = {
                    if (isSearchVisible) {
                        TextField(
                            value = searchQuery,
                            onValueChange = { searchQuery = it },
                            placeholder = { Text(text = "Search product...") },
                            singleLine = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(MaterialTheme.colorScheme.surfaceVariant)
                                .focusRequester(focusRequester),
                            colors = TextFieldDefaults.textFieldColors(
                                containerColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            )
                        )
                        LaunchedEffect(isSearchVisible) {
                            if (isSearchVisible) {
                                focusRequester.requestFocus()
                                keyboardController?.show()
                            }
                        }
                    } else {
                        Text(text = "Product List")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        if (isSearchVisible) {
                            searchQuery = TextFieldValue("")
                            keyboardController?.hide()
                        }
                        isSearchVisible = !isSearchVisible
                    }) {
                        Icon(
                            imageVector = if (isSearchVisible) Icons.Filled.Close else Icons.Filled.Search,
                            contentDescription = "Toggle Search"
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onFabClick() },
                shape = CircleShape,
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.White
            ) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Add")
            }
        },
        content = { paddingValues ->
            UiStateHandler(
                uiState = state,
                onSuccess = { productList ->
                    previousProductList = productList // Store latest success list

                    val filteredList = productList.filter {
                        it.product_name.contains(searchQuery.text, ignoreCase = true)
                    }

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues),
                        contentPadding = PaddingValues(Spacing.medium)
                    ) {
                        items(filteredList) { product ->
                            ProductItem(product = product)
                        }
                    }
                },
                onLoading = {
                    if (previousProductList.isNotEmpty()) {
                        // Show previous list while loading
                        val filteredList = previousProductList.filter {
                            it.product_name.contains(searchQuery.text, ignoreCase = true)
                        }

                        Box(modifier = Modifier.fillMaxSize()) {
                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(paddingValues),
                                contentPadding = PaddingValues(Spacing.medium)
                            ) {
                                items(filteredList) { product ->
                                    ProductItem(product = product)
                                }
                            }

                            // Show a loader over the existing list
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Color(0xAAFFFFFF)), // Semi-transparent overlay
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                    } else {
                        // If there's no previous list, show full loader
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                },

            )
        }
    )
}
