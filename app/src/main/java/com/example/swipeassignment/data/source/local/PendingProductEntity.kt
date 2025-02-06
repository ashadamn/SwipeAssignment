package com.example.swipeassignment.data.source.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "pending_products")
data class PendingProductEntity(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val productType: String,
    val productName: String,
    val sellingPrice: Double,
    val taxRate: Double,
    val imageBase64: String? = null,
    val isSynced: Boolean = false
)