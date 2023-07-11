package com.timothy.framework.ktx

import android.app.Application
import android.content.Context
import android.os.Build
import android.util.Log
import com.timothy.framework.ktx.utils.ProcessUtils
import kotlin.properties.Delegates

abstract class FWKtxApplication : Application(){

    override fun onCreate() {
        super.onCreate()
        _instance = this
    }

    override fun attachBaseContext(base: Context?) {
        val lastLivingInfo = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            ProcessUtils.getLastLivingTime(base, base?.packageName)
        }else{
            null
        }
        lastLivingInfo?.let {
            Log.d(FW_TAG, "lastLivingInfo time=${it.first} reason=${it.second}")
        }
        super.attachBaseContext(base)
    }

    companion object{
        const val FW_TAG = "FW_BASE"
        // 委托属性单例化
        private var _instance: FWKtxApplication by Delegates.notNull<FWKtxApplication>()

        val application: Application get() =  _instance
    }
}