package com.timothy.network

import com.timothy.network.builder.RetrofitBuilder
import com.timothy.network.config.ApiConfig
import retrofit2.Retrofit

class NetworkManager private constructor(){

    companion object{
        fun getInstance(): NetworkManager{
            return NetWorkManagerHolder.holder
        }
    }

    private object NetWorkManagerHolder{
        val holder by lazy { NetworkManager() }
    }

    private lateinit var apiConfig:ApiConfig

    private val mDefaultRetrofit: Retrofit by lazy {
        RetrofitBuilder()
            .setBaseUrl(apiConfig.baseUrl)
            .setConnectTimeout(apiConfig.defaultConnectTimeout)
            .setWriteTimeout(apiConfig.defaultWriteTimeout)
            .setReadTimeout(apiConfig.defaultReadTimeout)
            .setDns(apiConfig.dnsClient)
            .setCookieJar(apiConfig.cookieJar)
            .setConverterFactory(apiConfig.defaultConverterFactory)
            .setEventListener(apiConfig.eventListener)
            .setEventListenerFactory(apiConfig.eventListenerFactory)
            .setInterceptors(apiConfig.interceptors)
            .setNetworkInterceptors(apiConfig.networkInterceptors)
            .build()
    }

    private val mLongTimeRetrofit: Retrofit by lazy {
        RetrofitBuilder()
            .setBaseUrl(apiConfig.baseUrl)
            .setConnectTimeout(apiConfig.defaultFileConnectTimeout)
            .setWriteTimeout(apiConfig.defaultFileConnectTimeout)
            .setReadTimeout(apiConfig.defaultFileConnectTimeout)
            .setDns(apiConfig.dnsClient)
            .setCookieJar(apiConfig.cookieJar)
            .setConverterFactory(apiConfig.defaultConverterFactory)
            .setEventListener(apiConfig.eventListener)
            .setEventListenerFactory(apiConfig.eventListenerFactory)
            .setInterceptors(apiConfig.interceptors)
            .setNetworkInterceptors(apiConfig.networkInterceptors)
            .build()
    }

    val config get() = apiConfig

    val defaultClient get() = mDefaultRetrofit

    val longTimeRetrofit get() = mLongTimeRetrofit

    fun init(config: ApiConfig){
        apiConfig = config
    }

    fun <T : Any> createService(t: Class<T>): T{
        return mDefaultRetrofit.create(t)
    }

    fun <T: Any> createLongTimeService(t: Class<T>): T{
        return mLongTimeRetrofit.create(t)
    }
}