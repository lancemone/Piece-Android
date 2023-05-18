package com.timothy.piece

import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AnticipateInterpolator
import androidx.activity.viewModels
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.splashscreen.SplashScreenViewProvider
import androidx.lifecycle.flowWithLifecycle
import com.therouter.router.Route
import com.timothy.common.base.BaseActivity
import com.timothy.common.router.RouterPath
import com.timothy.piece.vm.MainViewModel
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOf

@Route(path = RouterPath.path_app_main)
class MainActivity : BaseActivity(), SplashScreen.OnExitAnimationListener{

    private val viewModel:MainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        splashScreen.setOnExitAnimationListener(this)
        splashScreen.setKeepOnScreenCondition {
            Log.d("Routing", "Routing splashScreen KeepOnScreenCondition")
            !viewModel.isDataReady.get()
        }
        super.onCreate(savedInstanceState)
        viewModel.initData()
    }

    private fun whenReady(){
    }


    override fun onSplashScreenExit(splashScreenViewProvider: SplashScreenViewProvider) {
        // Create your custom animation.
        val slideUp = ObjectAnimator.ofFloat(
            splashScreenViewProvider.view,
            View.TRANSLATION_Y,
            0f,
            -splashScreenViewProvider.view.height.toFloat()
        )
        slideUp.interpolator = AnticipateInterpolator()
        slideUp.duration = 200L

        // Call SplashScreenView.remove at the end of your custom animation.
        slideUp.doOnEnd {
            splashScreenViewProvider.remove()
            whenReady()
        }

        // Run your animation.
        slideUp.start()
    }
}