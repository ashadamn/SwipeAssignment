package com.example.swipeassignment.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PendingProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPendingProduct(product: PendingProductEntity)

    @Query("SELECT * FROM pending_products WHERE isSynced = 0")
    fun getUnSyncedProducts(): Flow<List<PendingProductEntity>>

    @Query("DELETE FROM pending_products WHERE id = :productId")
    suspend fun deletePendingProduct(productId: String)

}