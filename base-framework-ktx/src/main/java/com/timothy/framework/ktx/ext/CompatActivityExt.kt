package com.timothy.framework.ktx.ext

import androidx.activity.ComponentActivity
import com.timothy.framework.ktx.event.FlowEventBus
import com.timothy.framework.ktx.event.KTEvent

object CompatActivityExt {

    // crossinline
    inline fun <T: KTEvent> ComponentActivity.registerFlowEvent(name:String, crossinline block: (t: T) -> Unit){
        FlowEventBus.with<T>(name).register(this){
            block(it)
        }
    }


}