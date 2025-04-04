package com.jerry.domain.models

sealed interface DataResult<out T> {

    data class Success<out T>(
        val data: T
    ) : DataResult<T>

    data class Error(
        val message: String
    ) : DataResult<Nothing>
}