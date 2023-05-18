package com.timothy.network.exp


/**
 * @Project: Piece
 * @ClassPath:  com.timothy.network.exp.NetworkException
 * @Author: MoTao
 * @Date: 2023-05-17
 * <p>
 * <p/>
 */
class NetworkException(
    private var code: Int = -99,
    private var msg: String? = null
) : Exception("NetworkException Code($code) Error($msg)")