package com.jerry.sources.remote.models

sealed interface NetworkResult<out T> {

    data class Success<out T>(
        val data: T
    ) : NetworkResult<T>

    data class Error<out T>(
        val message: T
    ) : NetworkResult<Nothing>
}

suspend fun <T> safeApiCall(apiCall: suspend () -> T): NetworkResult<T> {
    return try {
        val result = apiCall()
        NetworkResult.Success(result)
    } catch (e: Exception) {
        NetworkResult.Error(e.message ?: "An error occurred")
    }
}
