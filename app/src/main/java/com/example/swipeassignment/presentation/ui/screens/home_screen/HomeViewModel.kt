package com.example.swipeassignment.presentation.ui.screens.home_screen

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.swipeassignment.data.model.add_product_response.AddProductResponseDTO
import com.example.swipeassignment.data.model.product_list_response.ProductListResponseDTO
import com.example.swipeassignment.data.repository.ProductRepository
import com.example.swipeassignment.domain.util.ApiResult
import com.example.swipeassignment.domain.util.toApiResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: ProductRepository
) : ViewModel() {

    private val _productListState =
        MutableStateFlow<ApiResult<List<ProductListResponseDTO>>>(ApiResult.Loading)
    val productListState: StateFlow<ApiResult<List<ProductListResponseDTO>>> =
        _productListState.asStateFlow()

    init {
        loadProductList()
    }

    fun loadProductList() {
        _productListState.value = ApiResult.Loading
        viewModelScope.launch {
            val response = repository.getProductList()
            _productListState.value = response.toApiResult()
        }
    }

    private val _addProductState =
        MutableStateFlow<ApiResult<AddProductResponseDTO>>(ApiResult.Loading)
    val addProductState: StateFlow<ApiResult<AddProductResponseDTO>> =
        _addProductState.asStateFlow()

    fun addProduct(
        productType: String,
        productName: String,
        sellingPrice: Double,
        taxRate: Double,
        selectedImage: Uri?
    ) {
        _addProductState.value = ApiResult.Loading
        viewModelScope.launch {
            val response = repository.addProduct(
                productType,
                productName,
                sellingPrice,
                taxRate,
                selectedImage
            )
            _addProductState.value = response.toApiResult()
        }
    }

}