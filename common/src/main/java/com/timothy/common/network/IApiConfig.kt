package com.timothy.common.network

import android.util.Log
import com.timothy.network.config.ApiConfig
import okhttp3.CookieJar
import okhttp3.Dns
import okhttp3.EventListener
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.LoggingEventListener
import retrofit2.Converter


/**
 * @Project: Piece
 * @ClassPath:  com.timothy.common.network.IApiConfig
 * @Author: MoTao
 * @Date: 2023-05-10
 * <p>
 * <p/>
 */
class IApiConfig : ApiConfig {
    companion object{
        const val API_TAG = "P_Api"

        private val myHttpLogger =
            HttpLoggingInterceptor.Logger { message -> Log.d(API_TAG, message) }
    }

    override val baseUrl: String
        get() = "http://su-timothy.com/piece/api"
    override val defaultConnectTimeout: Long
        get() = 20
    override val defaultReadTimeout: Long
        get() = 20
    override val defaultWriteTimeout: Long
        get() = 20
    override val defaultFileConnectTimeout: Long
        get() = 60
    override val defaultFileReadTimeout: Long
        get() = 600
    override val defaultFileWriteTimeout: Long
        get() = 600

    override val interceptors: List<Interceptor>
        get() = arrayListOf(
            CommonHeaderInterceptor()
        )

    override val networkInterceptors: List<Interceptor>
        get() = arrayListOf(
            HttpLoggingInterceptor(myHttpLogger)
        )

    override val defaultConverterFactory: Converter.Factory
        get() = super.defaultConverterFactory

    override val dnsClient: Dns?
        get() = super.dnsClient

    override val cookieJar: CookieJar?
        get() = super.cookieJar

    override val eventListener: EventListener
        get() = INetworkEventListener()

    override val eventListenerFactory: EventListener.Factory
        get() = LoggingEventListener.Factory(myHttpLogger)
}