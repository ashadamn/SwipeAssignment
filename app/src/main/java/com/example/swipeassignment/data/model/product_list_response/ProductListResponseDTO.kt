package com.example.swipeassignment.data.model.product_list_response

import kotlinx.serialization.Serializable

@Serializable
data class ProductListResponseDTO(
    val image: String,
    val price: Double,
    val product_name: String,
    val product_type: String,
    val tax: Double
)