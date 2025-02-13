package ru.shvetsov.common.utils

import retrofit2.Response

abstract class BaseApiResponse {

    suspend fun <T, R> handleApiCall(
        api: suspend () -> Response<T>,
        mapper: (T) -> R
    ): Result<R> {
        try {
            val response = api()
            if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    return Result.success( mapper(body))
                } ?: return errorMessage("Body is empty")
            } else {
                return errorMessage("${response.code()} ${response.message()}")
            }
        } catch (e: Exception) {
            return errorMessage(e.message.toString())
        }
    }

    private fun <T> errorMessage(e: String): Result<T> =
        Result.failure(Exception(e))
}