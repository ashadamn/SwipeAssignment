package com.example.swipeassignment.data.worker

import android.content.Context
import android.net.Uri
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.swipeassignment.data.repository.ProductRepository
import com.example.swipeassignment.data.source.local.PendingProductDao
import com.example.swipeassignment.data.util.ResponseState
import kotlinx.coroutines.flow.first
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ProductSyncWorker(
    context: Context,
    params: WorkerParameters,
) : CoroutineWorker(context, params), KoinComponent {

    private val productRepository: ProductRepository by inject()
    private val pendingProductDao: PendingProductDao by inject()

    override suspend fun doWork(): Result {
        return try {
            // Fetch unsyced products and attempt to sync
            val unSyncedProducts = productRepository.getUnSyncedProducts().first()

            unSyncedProducts.forEach { pendingProduct ->
                val response = productRepository.addProduct(
                    productType = pendingProduct.productType,
                    productName = pendingProduct.productName,
                    sellingPrice = pendingProduct.sellingPrice,
                    taxRate = pendingProduct.taxRate,
                    selectedImage = pendingProduct.imageBase64?.let { Uri.parse(it) }
                )

                if (response is ResponseState.Success) {
                    pendingProductDao.deletePendingProduct(pendingProduct.id)
                }

            }

            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}