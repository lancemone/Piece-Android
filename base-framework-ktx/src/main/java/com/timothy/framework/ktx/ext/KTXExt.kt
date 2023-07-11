package com.timothy.framework.ktx.ext

object KTXExt {

    fun Byte.toHexString():String{
        return String.format("%02x", this)
    }
}