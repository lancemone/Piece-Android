package com.timothy.network.response

sealed class RequestResult<T>(
    val data: T? = null,
    val error: Exception? = null
){
    class SuccessResult<T>(data: T): RequestResult<T>(data = data)

    class ErrorResult<T>(exp: Exception, data: T? = null): RequestResult<T>(data = data, error = exp)

    class EmptyResult<T>: RequestResult<T>()

    class LoadingResult<T>: RequestResult<T>()
}
