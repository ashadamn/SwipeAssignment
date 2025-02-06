package com.example.swipeassignment.data.repository

import android.content.Context
import android.net.Uri
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.swipeassignment.data.model.add_product_response.AddProductResponseDTO
import com.example.swipeassignment.data.model.product_list_response.ProductListResponseDTO
import com.example.swipeassignment.data.source.local.PendingProductDao
import com.example.swipeassignment.data.source.local.PendingProductEntity
import com.example.swipeassignment.data.source.remote.AppApiService
import com.example.swipeassignment.data.util.ResponseState
import com.example.swipeassignment.data.util.getApiResponseState
import com.example.swipeassignment.data.worker.ProductSyncWorker
import com.example.swipeassignment.util.toBase64
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class ProductRepositoryImpl(
    private val apiService: AppApiService,
    private val pendingProductDao: PendingProductDao,
    private val workManager: WorkManager,
    private val context: Context
) : ProductRepository {
    override suspend fun getProductList(): ResponseState<List<ProductListResponseDTO>> {
        return getApiResponseState {
            withContext(Dispatchers.IO) {
                apiService.getProductList()
            }
        }
    }

    override suspend fun addProduct(
        productType: String,
        productName: String,
        sellingPrice: Double,
        taxRate: Double,
        selectedImage: Uri?
    ): ResponseState<AddProductResponseDTO> {
        return try {
            val response = getApiResponseState {
                withContext(Dispatchers.IO) {
                    apiService.addProduct(
                        productType,
                        productName,
                        sellingPrice,
                        taxRate,
                        selectedImage
                    )
                }
            }

            when (response) {
                is ResponseState.Success -> response
                else -> {
                    // If API call fails, save locally and schedule sync
                    savePendingProduct(
                        productType,
                        productName,
                        sellingPrice,
                        taxRate,
                        selectedImage
                    )
                    ResponseState.Success(
                        AddProductResponseDTO(
                            success = true,
                            message = "Product saved offline. Will sync when online",
                            product_details = null,
                            product_id = null
                        )
                    )
                }
            }
        } catch (e: Exception) {
            // On any error, save locally and schedule sync
            savePendingProduct(
                productType,
                productName,
                sellingPrice,
                taxRate,
                selectedImage
            )
            ResponseState.Success(
                AddProductResponseDTO(
                    success = true,
                    message = "Product saved offline. Will sync when online",
                    product_details = null,
                    product_id = null
                )
            )
        }
    }

    override suspend fun savePendingProduct(
        productType: String,
        productName: String,
        sellingPrice: Double,
        taxRate: Double,
        selectedImage: Uri?
    ) {
        val pendingProduct = PendingProductEntity(
            productType = productType,
            productName = productName,
            sellingPrice = sellingPrice,
            taxRate = taxRate,
            imageBase64 = selectedImage?.toBase64(context) // You'll need to implement proper image conversion
        )

        withContext(Dispatchers.IO) {
            pendingProductDao.insertPendingProduct(pendingProduct)
        }

        // Schedule sync work
        scheduleSyncWork()
    }

    override fun getUnSyncedProducts(): Flow<List<PendingProductEntity>> {
        return pendingProductDao.getUnSyncedProducts()
    }

    override suspend fun syncPendingProducts() {
        val unSyncedProducts = pendingProductDao.getUnSyncedProducts()
        unSyncedProducts.collect { products ->
            products.forEach { product ->
                try {
                    val response = getApiResponseState {
                        withContext(Dispatchers.IO) {
                            apiService.addProduct(
                                product.productType,
                                product.productName,
                                product.sellingPrice,
                                product.taxRate,
                                product.imageBase64?.let { Uri.parse(it) }
                            )
                        }
                    }

                    if (response is ResponseState.Success) {
                        // Delete successfully synced product from local DB
                        withContext(Dispatchers.IO) {
                            pendingProductDao.deletePendingProduct(product.id)
                        }
                    }
                } catch (e: Exception) {
                    // If sync fails, product remains in DB for next attempt
                }
            }
        }
    }

    private fun scheduleSyncWork() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val syncWorkRequest = OneTimeWorkRequestBuilder<ProductSyncWorker>()
            .setConstraints(constraints)
            .build()

        workManager.enqueue(syncWorkRequest)
    }
}