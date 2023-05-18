package com.timothy.common.network


/**
 * @Project: Piece
 * @ClassPath:  com.timothy.common.network.ResponseEntnty
 * @Author: MoTao
 * @Date: 2023-05-18
 * <p>
 * <p/>
 */
data class ResponseEntity<T> (
    val code: Int = -1,
    val message:String? = null,
    val data: T? = null
){

}