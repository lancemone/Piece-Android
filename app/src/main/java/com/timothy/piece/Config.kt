package com.timothy.piece

import android.util.Log

object Config {

    private lateinit var mName:String

    init {
        if (!this::mName.isInitialized){
            Log.d("Piece", "Config init")
            mName = "Default"
        }
    }

    fun setName(name:String){
        Log.d("Piece", "Config setName")
        this.mName = name
    }
}