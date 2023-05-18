package com.timothy.network.builder

import okhttp3.CookieJar
import okhttp3.Dns
import okhttp3.EventListener
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * @Project: Piece
 * @ClassPath:  com.timothy.network.builder.RetrofitBuilder
 * @Author: MoTao
 * @Date: 2023-05-11
 * <p>
 * <p/>
 */
class RetrofitBuilder {
    private var connectTimeout: Long = 20L
    private var readTimeout:Long = 20L
    private var writeTimeout: Long = 20L
    private var baseUrl:String = ""
    private var converterFactory: Converter.Factory? = null
    private var cookieJar: CookieJar? = null
    private var eventListener: EventListener? = null
    private var eventListenerFactory: EventListener.Factory? = null
    private var interceptors: List<Interceptor>? = null
    private var networkInterceptors: List<Interceptor>? = null
    private var dnsClient:Dns? = null

    fun setConnectTimeout(timeout: Long): RetrofitBuilder {
        connectTimeout = timeout
        return this
    }

    fun setReadTimeout(timeout: Long): RetrofitBuilder{
        readTimeout = timeout
        return this
    }

    fun setWriteTimeout(timeout: Long): RetrofitBuilder{
        writeTimeout = timeout
        return this
    }

    fun setBaseUrl(url:String): RetrofitBuilder{
        baseUrl = url
        return this
    }

    fun setConverterFactory(converterFactory: Converter.Factory?): RetrofitBuilder{
        this.converterFactory = converterFactory
        return this
    }

    fun setCookieJar(jar: CookieJar?): RetrofitBuilder{
        cookieJar = jar
        return this
    }

    fun setEventListener(listener: EventListener?): RetrofitBuilder{
        eventListener = listener
        return this
    }

    fun setEventListenerFactory(factory: EventListener.Factory?): RetrofitBuilder{
        eventListenerFactory = factory
        return this
    }

    fun setInterceptors(interceptors: List<Interceptor>?): RetrofitBuilder{
        this.interceptors = interceptors
        return this
    }

    fun setNetworkInterceptors(interceptors: List<Interceptor>?): RetrofitBuilder{
        this.networkInterceptors = interceptors
        return this
    }

    fun setDns(dns: Dns?): RetrofitBuilder{
        dnsClient = dns
        return this
    }

    fun build(): Retrofit{
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(connectTimeout, TimeUnit.SECONDS)
            .writeTimeout(writeTimeout, TimeUnit.SECONDS)
            .readTimeout(readTimeout, TimeUnit.SECONDS)
        dnsClient?.let {
            okHttpClient.dns(it)
        }
        eventListener?.let {
            okHttpClient.eventListener(it)
        }
        eventListenerFactory?.let {
            okHttpClient.eventListenerFactory(it)
        }
        cookieJar?.let {
            okHttpClient.cookieJar(it)
        }
        interceptors?.forEach {
            okHttpClient.addInterceptor(it)
        }
        networkInterceptors?.forEach {
            okHttpClient.addNetworkInterceptor(it)
        }

        val builder = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient.build())
        converterFactory?.let {
            builder.addConverterFactory(it)
        }
//        builder.addCallAdapterFactory()
        return builder.build()
    }
}