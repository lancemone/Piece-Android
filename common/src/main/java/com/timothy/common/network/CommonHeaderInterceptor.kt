package com.timothy.common.network

import com.timothy.framework.ktx.FWKtxApplication
import okhttp3.Interceptor
import okhttp3.Response


/**
 * @Project: Piece
 * @ClassPath:  com.timothy.common.network.CommonHeaderInterceptor
 * @Author: MoTao
 * @Date: 2023-05-17
 * <p>
 * <p/>
 */
class CommonHeaderInterceptor : Interceptor{
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        builder.addHeader("packageName", FWKtxApplication.application.packageName)
        return chain.proceed(builder.build())
    }
}