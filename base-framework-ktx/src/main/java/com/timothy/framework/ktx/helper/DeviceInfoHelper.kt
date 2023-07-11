package com.timothy.framework.ktx.helper

import android.app.Activity
import android.os.Build

object DeviceInfoHelper {

    /**
     * 获取手机品牌
     */
    fun getPhoneBrand():String {
        return Build.BRAND
    }

    fun getPhoneModel():String{
        return Build.MODEL
    }

    /**
     * 获取手机分辨率
     * @return [宽, 高]
     */
    fun getPhoneResolution(activity: Activity):Array<Int>{
        val wm = activity.windowManager
        val screenWidth = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            activity.windowManager.currentWindowMetrics.bounds.width()
        }else{
            wm.defaultDisplay.width
        }
        val screenHeight = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            activity.windowManager.currentWindowMetrics.bounds.height()
        }else{
            wm.defaultDisplay.height
        }
        return arrayOf(screenWidth, screenHeight)
    }
}