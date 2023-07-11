package com.timothy.nativelib

import android.util.Log

object NativeLib {

    init {
        try {
            System.loadLibrary("nativelib")
            Log.d("Piece", "loadLibrary nativelib")
        }catch (e: Exception){
            e.printStackTrace()
        }

    }

    /**
     * A native method that is implemented by the 'nativelib' native library,
     * which is packaged with this application.
     */
    external fun strEncrypt(string: String): String

    external fun strDecrypt(string: String): String

//    companion object {
//        // Used to load the 'nativelib' library on application startup.
//
//    }
}