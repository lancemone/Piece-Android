package com.timothy.piece.data

data class SimpleData(
    var name:Int = 1,
    var arg:Int = 23
){
    fun getFieldValue(name:String): Int{
        return try {
            this.javaClass.getDeclaredField(name).getInt(this)
        }catch (e: NoSuchFieldException){
            0
        }
    }
}
