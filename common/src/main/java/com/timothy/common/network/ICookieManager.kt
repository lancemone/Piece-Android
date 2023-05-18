package com.timothy.common.network

import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl


/**
 * @Project: Piece
 * @ClassPath:  com.timothy.common.network.ICookieManager
 * @Author: MoTao
 * @Date: 2023-05-18
 * <p>
 * <p/>
 */
class ICookieManager : CookieJar{
    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        return emptyList()
    }

    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {

    }
}