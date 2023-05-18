package com.timothy.network.config

import okhttp3.CookieJar
import okhttp3.Dns
import okhttp3.EventListener
import okhttp3.Interceptor
import retrofit2.Converter
import retrofit2.converter.gson.GsonConverterFactory


/**
 * @Project: Piece
 * @ClassPath:  com.timothy.network.config.ApiConfig
 * @Author: MoTao
 * @Date: 2023-05-10
 * <p>
 * <p/>
 */
interface ApiConfig {
    val baseUrl:String  // 公共URL部分

    // SECONDS
    val defaultConnectTimeout: Long

    val defaultReadTimeout:Long

    val defaultWriteTimeout:Long

    val defaultFileConnectTimeout: Long

    val defaultFileReadTimeout:Long

    val defaultFileWriteTimeout:Long

    val defaultConverterFactory: Converter.Factory
        get() = GsonConverterFactory.create()

    val supportRxJava: Boolean
        get() = false

    val isRxJava3: Boolean
        get() = false

    val cookieJar: CookieJar? get() = null

    val eventListener:EventListener?
        get() = null

    val eventListenerFactory: EventListener.Factory?
        get() = null

//    val commonHeaderInterceptor: Interceptor

    val interceptors: List<Interceptor>?
        get() = null

    val networkInterceptors: List<Interceptor>?
        get() = null

    val dnsClient:Dns?
        get() = null
}