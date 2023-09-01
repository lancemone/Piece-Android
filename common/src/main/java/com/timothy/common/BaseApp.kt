package com.timothy.common

import android.content.Context
import coil.ImageLoader
import coil.ImageLoaderFactory
import com.timothy.common.network.IApiConfig
import com.timothy.framework.ktx.FWKtxApplication
import com.timothy.network.NetworkManager

abstract class BaseApp : FWKtxApplication(), ImageLoaderFactory{

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
    }

    override fun onCreate() {
        super.onCreate()
        NetworkManager.getInstance().init(IApiConfig())
    }

    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(this)
            .crossfade(true)
            .crossfade(500)
            .build()
    }
}