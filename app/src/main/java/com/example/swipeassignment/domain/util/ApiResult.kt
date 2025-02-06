package com.example.swipeassignment.domain.util

sealed interface ApiResult<out T> {
    data object Loading : ApiResult<Nothing>
    data object Idle : ApiResult<Nothing>
    data object NoInternet : ApiResult<Nothing>
    data class Success<T>(val data: T) : ApiResult<T>
    data class ErrorMessage(val message: String) : ApiResult<Nothing>
}