package com.timothy.common

import android.content.Context
import com.therouter.TheRouter
import com.timothy.framework.ktx.FWKtxApplication
import com.timothy.framework.ktx.factory.DataStoreFactory

abstract class BaseApp : FWKtxApplication(){

    override fun attachBaseContext(base: Context?) {
        TheRouter.isDebug = true
        super.attachBaseContext(base)
        DataStoreFactory.init(this)
    }
}