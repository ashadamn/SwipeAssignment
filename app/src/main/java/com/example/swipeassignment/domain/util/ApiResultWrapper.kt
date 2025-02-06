package com.example.swipeassignment.domain.util

import com.example.swipeassignment.data.util.ResponseState


fun <T> ResponseState<T>.toApiResult(): ApiResult<T> {
    return when (this) {
        is ResponseState.Success -> {
            ApiResult.Success(this.data)
        }

        is ResponseState.ErrorMessage -> {
            ApiResult.ErrorMessage(this.message)
        }

        is ResponseState.NoInternet -> {
            ApiResult.NoInternet
        }
    }
}