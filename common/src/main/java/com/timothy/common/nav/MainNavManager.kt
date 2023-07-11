package com.timothy.common.nav

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import java.lang.ref.WeakReference

class MainNavManager private constructor(val lifecycleOwner: LifecycleOwner){

    private val navController: WeakReference<NavController> = WeakReference(null)
    fun nav(){

    }
}