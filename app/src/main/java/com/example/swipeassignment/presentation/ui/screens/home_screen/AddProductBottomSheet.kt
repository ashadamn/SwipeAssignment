package com.example.swipeassignment.presentation.ui.screens.home_screen

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.swipeassignment.data.model.add_product_response.AddProductResponseDTO
import com.example.swipeassignment.domain.util.ApiResult

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProductBottomSheet(
    state: ApiResult<AddProductResponseDTO>,
    onSave: (productType: String, productName: String, sellingPrice: Double, taxRate: Double, selectedImage: Uri?) -> Unit
) {
    val context = LocalContext.current

    var productType by remember { mutableStateOf("") }
    var productName by remember { mutableStateOf("") }
    var sellingPrice by remember { mutableStateOf("") }
    var taxRate by remember { mutableStateOf("") }
    var selectedImage by remember { mutableStateOf<Uri?>(null) }

    var isLoading by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(state) {
        if (state is ApiResult.Success) {
            productType = ""
            productName = ""
            sellingPrice = ""
            taxRate = ""
            selectedImage = null
            isLoading = false
        }
    }

    // Validation states
    var isProductTypeError by remember { mutableStateOf(false) }
    var isProductNameError by remember { mutableStateOf(false) }
    var isSellingPriceError by remember { mutableStateOf(false) }
    var isTaxRateError by remember { mutableStateOf(false) }

    var expanded by remember { mutableStateOf(false) }

    val productTypes = listOf("Electronics", "Clothing", "Groceries", "Other")

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            val mimeType = context.contentResolver.getType(it)
            val validMimeTypes = listOf("image/jpeg", "image/png")

            if (mimeType in validMimeTypes) {
                selectedImage = uri
            } else {
                Toast.makeText(context, "Images must me JPEG or PNG", Toast.LENGTH_SHORT).show()
            }
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Add Product") }
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = it }
                ) {
                    OutlinedTextField(
                        value = productType,
                        onValueChange = {},
                        label = { Text("Product Type") },
                        readOnly = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(),
                        trailingIcon = {
                            Icon(
                                Icons.Default.ArrowDropDown,
                                contentDescription = "Dropdown Icon",
                                modifier = Modifier.clickable { expanded = !expanded }
                            )
                        },
                        isError = isProductTypeError
                    )

                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        productTypes.forEach { selectedProductType ->
                            DropdownMenuItem(
                                onClick = {
                                    productType = selectedProductType
                                    expanded = false
                                },
                                text = { Text(text = selectedProductType) }
                            )
                        }
                    }
                }

                OutlinedTextField(
                    value = productName,
                    onValueChange = { productName = it },
                    label = { Text("Product Name") },
                    modifier = Modifier.fillMaxWidth(),
                    isError = isProductNameError
                )

                OutlinedTextField(
                    value = sellingPrice,
                    onValueChange = { sellingPrice = it },
                    label = { Text("Selling Price") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    isError = isSellingPriceError
                )

                OutlinedTextField(
                    value = taxRate,
                    onValueChange = { taxRate = it },
                    label = { Text("Tax") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    isError = isTaxRateError
                )

                // Image Picker
                Button(
                    onClick = {
                        imagePickerLauncher.launch("image/*")
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Select Image (JPEG/PNG, 1:1 Ratio)")
                }

                selectedImage?.let { imageUri ->
                    AsyncImage(
                        model = imageUri,
                        contentDescription = null,
                        modifier = Modifier
                            .size(100.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                }

                Button(
                    onClick = {
                        isProductTypeError = productType.isEmpty()
                        isProductNameError = productName.isEmpty()
                        isSellingPriceError = sellingPrice.toDoubleOrNull() == null
                        isTaxRateError = taxRate.toDoubleOrNull() == null

                        if (!isProductTypeError && !isProductNameError && !isSellingPriceError && !isTaxRateError) {
                            onSave(
                                productType,
                                productName,
                                sellingPrice.toDouble(),
                                taxRate.toDouble(),
                                selectedImage
                            )
                            isLoading = true
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !isLoading
                ) {
                    if (isLoading) {
                        CircularProgressIndicator()
                    } else {
                        Text("Save Product")
                    }
                }
            }
        }
    )

}
