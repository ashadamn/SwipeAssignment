package com.example.swipeassignment.data.util

import android.util.Log
import java.io.IOException

sealed interface ResponseState<out T> {
    data class Success<R>(val data: R) : ResponseState<R>
    data object NoInternet : ResponseState<Nothing>
    data class ErrorMessage(val message: String) : ResponseState<Nothing>
}

suspend fun <T> getApiResponseState(
    request: suspend () -> T
): ResponseState<T> {
    return try {
        val response = request()
        ResponseState.Success(response)
    } catch (e: IOException) {
        ResponseState.NoInternet
    } catch (e: Exception) {
        Log.e("ERROR_PARSING", "getApiResponseState: ${e.message}")
        ResponseState.ErrorMessage(e.message ?: "")
    }
}