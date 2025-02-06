package com.example.swipeassignment.data.source.remote

import android.content.Context
import android.net.Uri
import com.example.swipeassignment.data.model.add_product_response.AddProductResponseDTO
import com.example.swipeassignment.data.model.product_list_response.ProductListResponseDTO
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType

class AppApiService(private val client: HttpClient, private val context: Context) {

    suspend fun getProductList(): List<ProductListResponseDTO> {
        return client.get("api/public/get") {
        }.body()
    }

    suspend fun addProduct(
        productType: String,
        productName: String,
        sellingPrice: Double,
        taxRate: Double,
        selectedImage: Uri?
    ): AddProductResponseDTO {
        return client.post("api/public/add") {
            contentType(ContentType.MultiPart.FormData)

            setBody(MultiPartFormDataContent(formData {
                append("product_name", productName)
                append("product_type", productType)
                append("price", sellingPrice.toString())
                append("tax", taxRate.toString())

                selectedImage?.let { uri ->
                    val inputStream = context.contentResolver.openInputStream(uri)
                    val bytes = inputStream?.readBytes()

                    if (bytes != null) {
                        append(
                            key = "files[]",
                            value = bytes,
                            headers = Headers.build {
                                append(
                                    HttpHeaders.ContentDisposition,
                                    "form-data; name=\"files[]\"; filename=\"image.jpg\""
                                )
                                append(HttpHeaders.ContentType, "image/jpeg")
                            }
                        )
                    }
                    inputStream?.close()
                }
            }))
        }.body()
    }

}