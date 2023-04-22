package com.timothy.framework.ktx

import android.app.Application
import android.content.Context
import kotlin.properties.Delegates

abstract class FWKtxApplication : Application(){

    override fun onCreate() {
        super.onCreate()
        _instance = this
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
    }

    companion object{
        // 委托属性单例化
        private var _instance: FWKtxApplication by Delegates.notNull<FWKtxApplication>()

        @JvmStatic
        val application: Application = _instance
    }
}