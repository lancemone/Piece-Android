package com.timothy.framework.ktx.helper

import android.media.Ringtone
import android.media.RingtoneManager
import com.timothy.framework.ktx.FWKtxApplication

object RingtoneHelper {

    private var rt: Ringtone? = null

    /**
     * 响铃
     */
    fun playRing(){
        runCatching {
            val uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE)
            rt = RingtoneManager.getRingtone(FWKtxApplication.application, uri)
            rt!!.play()
        }
    }

    fun stopRing(){
        runCatching {
            rt?.stop()
            rt = null
        }
    }
}