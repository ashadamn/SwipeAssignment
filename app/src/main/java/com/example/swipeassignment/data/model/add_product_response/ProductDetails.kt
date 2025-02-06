package com.example.swipeassignment.data.model.add_product_response

import kotlinx.serialization.Serializable

@Serializable
data class ProductDetails(
    val image: String,
    val price: Double,
    val product_name: String,
    val product_type: String,
    val tax: Double
)