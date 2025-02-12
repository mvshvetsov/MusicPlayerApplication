package ru.shvetsov.common.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class BaseNetworkUseCase {

    fun <T> handleRequest(
        request: suspend () -> Result<T>,
    ): Flow<NetworkResult<T>> {
        return flow {
            emit(NetworkResult.Loading())
            val response = request()
            if (response.isSuccess) {
                emit(NetworkResult.Success(data = response.getOrThrow()))
            } else {
                emit(NetworkResult.Error(message = response.exceptionOrNull()?.localizedMessage))
            }
        }.catch {
            emit(NetworkResult.Error(message = it.message.toString()))
        }.flowOn(Dispatchers.IO)
    }
}