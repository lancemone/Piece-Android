package com.timothy.piece

import android.content.Context
import androidx.multidex.MultiDex
import com.timothy.common.BaseApp

class PieceApp : BaseApp(){

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this);
    }
}