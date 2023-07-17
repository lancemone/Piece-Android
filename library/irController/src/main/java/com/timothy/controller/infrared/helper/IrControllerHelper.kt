package com.timothy.controller.infrared.helper

import android.content.Context
import android.hardware.ConsumerIrManager
import com.timothy.common.BaseApp
import com.timothy.framework.ktx.FWKtxApplication

object IrControllerHelper {

    /**
     * @return 检查设备是否具有红外发射器。返回true表示有，返回false表示没有
     */
    fun hasIrEmitter(): Boolean{
        try {
            val irService: ConsumerIrManager? = FWKtxApplication.application.getSystemService(Context.CONSUMER_IR_SERVICE) as ConsumerIrManager?
            return irService?.hasIrEmitter() == true
        }catch (e: Exception){
            e.printStackTrace()
        }
        return false
    }

    /**
     * 获得红外发射器可用的载波频率范围
     */
    fun getCarrierFrequencies(): Array<out ConsumerIrManager.CarrierFrequencyRange>?{
        try {
            val irService: ConsumerIrManager? = FWKtxApplication.application.getSystemService(Context.CONSUMER_IR_SERVICE) as ConsumerIrManager?
            return irService?.carrierFrequencies
        }catch (e: Exception){
            e.printStackTrace()
        }
        return null
    }
}