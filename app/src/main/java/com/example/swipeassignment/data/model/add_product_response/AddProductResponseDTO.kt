package com.example.swipeassignment.data.model.add_product_response

import kotlinx.serialization.Serializable

@Serializable
data class AddProductResponseDTO(
    val message: String,
    val product_details: ProductDetails?,
    val product_id: Int?,
    val success: Boolean
)