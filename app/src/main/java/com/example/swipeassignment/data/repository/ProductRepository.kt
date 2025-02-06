package com.example.swipeassignment.data.repository

import android.net.Uri
import com.example.swipeassignment.data.model.add_product_response.AddProductResponseDTO
import com.example.swipeassignment.data.model.product_list_response.ProductListResponseDTO
import com.example.swipeassignment.data.source.local.PendingProductEntity
import com.example.swipeassignment.data.util.ResponseState
import kotlinx.coroutines.flow.Flow

interface ProductRepository {

    suspend fun getProductList(): ResponseState<List<ProductListResponseDTO>>

    suspend fun addProduct(
        productType: String,
        productName: String,
        sellingPrice: Double,
        taxRate: Double,
        selectedImage: Uri?
    ): ResponseState<AddProductResponseDTO>

    suspend fun savePendingProduct(
        productType: String,
        productName: String,
        sellingPrice: Double,
        taxRate: Double,
        selectedImage: Uri?
    )

    fun getUnSyncedProducts(): Flow<List<PendingProductEntity>>

    suspend fun syncPendingProducts()
}