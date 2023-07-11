package com.timothy.common

import android.content.Context
//import com.therouter.TheRouter
import com.timothy.common.network.IApiConfig
import com.timothy.framework.ktx.FWKtxApplication
import com.timothy.framework.ktx.factory.DataStoreFactory
import com.timothy.network.NetworkManager

abstract class BaseApp : FWKtxApplication(){

    override fun attachBaseContext(base: Context?) {
//        TheRouter.isDebug = true
        super.attachBaseContext(base)
    }

    override fun onCreate() {
        super.onCreate()
        NetworkManager.getInstance().init(IApiConfig())
    }
}