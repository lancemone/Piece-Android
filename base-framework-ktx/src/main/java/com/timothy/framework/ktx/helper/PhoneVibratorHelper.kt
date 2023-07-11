package com.timothy.framework.ktx.helper

import android.app.Service
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import com.timothy.framework.ktx.FWKtxApplication


/**
 * 手机振动控制
 */
object PhoneVibratorHelper {

    const val DEFAULT_VIBRATOR_STRENGTH: Int = 100   // 默认振动强度

    val DEFAULT_TIMINGS: Array<Long> = arrayOf(100, 1000)

    const val VIBRATOR_REPEAT = 0

    const val VIBRATOR_NOT_REPEAT = -1

    /**
     * 震动指定时间
     *
     * @param mills 毫秒
     */
    fun vibrate(mills: Long, amplitude: Int= DEFAULT_VIBRATOR_STRENGTH) {
        runCatching {
            val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                val manager: VibratorManager = FWKtxApplication.application.getSystemService(Service.VIBRATOR_MANAGER_SERVICE) as VibratorManager
                manager.defaultVibrator
            } else {
                FWKtxApplication.application.getSystemService(Service.VIBRATOR_SERVICE) as Vibrator
            }

            if (vibrator.hasVibrator()){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    vibrator.vibrate(VibrationEffect.createOneShot(mills, amplitude))
                }else{
                    vibrator.vibrate(mills)
                }
            }
        }
    }

    /**
     * 以pattern[]方式震动
     * repeat -1 不重复  0一直震动
     */
    fun vibrate(pattern: LongArray, repeat: Int){
        kotlin.runCatching {
            val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                val manager: VibratorManager = FWKtxApplication.application.getSystemService(Service.VIBRATOR_MANAGER_SERVICE) as VibratorManager
                manager.defaultVibrator
            } else {
                FWKtxApplication.application.getSystemService(Service.VIBRATOR_SERVICE) as Vibrator
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createWaveform(pattern, repeat))
            }else{
                vibrator.vibrate(pattern,repeat)
            }
        }
    }

    fun vibrateCancel(){
        runCatching {
            val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                val manager: VibratorManager = FWKtxApplication.application.getSystemService(Service.VIBRATOR_MANAGER_SERVICE) as VibratorManager
                manager.defaultVibrator
            } else {
                FWKtxApplication.application.getSystemService(Service.VIBRATOR_SERVICE) as Vibrator
            }
            vibrator.cancel()
        }
    }
}