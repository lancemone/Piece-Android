package com.timothy.network.request

import com.timothy.network.exp.NetworkException
import com.timothy.network.response.RequestResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response


/**
 * @Project: Piece
 * @ClassPath:  com.timothy.network.request.BaseApiRequest
 * @Author: MoTao
 * @Date: 2023-05-17
 * <p>
 * <p/>
 */
abstract class BaseApiRequest {

    suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): Flow<RequestResult<T>> {
        var result: RequestResult<T> = RequestResult.LoadingResult()
        val response = try {
            apiCall()
        }catch (e: Exception){
            result = RequestResult.ErrorResult<T>(e, null)
            null
        }

        if (response != null){
            result = if (response.isSuccessful){
                if (response.body() != null){
                    RequestResult.SuccessResult(response.body()!!)
                }else{
                    RequestResult.EmptyResult()
                }
            }else{
                RequestResult.ErrorResult(NetworkException(response.code(), response.body()?.toString()))
            }
        }

        return flow<RequestResult<T>>{
            emit(result)
        }.flowOn(Dispatchers.IO)
    }
}