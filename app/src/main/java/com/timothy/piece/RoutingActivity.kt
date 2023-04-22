package com.timothy.piece

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import android.view.animation.AnticipateInterpolator
import androidx.activity.viewModels
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.viewModelScope
import com.timothy.common.base.BaseActivity
import com.timothy.common.base.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicBoolean

class RoutingActivity : BaseActivity(){

    companion object {
    }

    private val splashExit = SplashScreen.OnExitAnimationListener { splashScreenViewProvider ->
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
            toNextActivity()
        }

        // Run your animation.
        slideUp.start()
    }

    private val viewModel: SplashViewModel by viewModels<SplashViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        delegate.applyDayNight()
        Log.d("Routing", "splashScreen $splashScreen  applyDayNight=${delegate.applyDayNight()}")
        splashScreen.setKeepOnScreenCondition {
            Log.d("Routing", "Routing splashScreen KeepOnScreenCondition")
            !viewModel.isDataReady.get()
        }
        splashScreen.setOnExitAnimationListener(splashExit)
        super.onCreate(savedInstanceState)
        viewModel.initData()
//        val content: View = findViewById(android.R.id.content)
//        content.viewTreeObserver.addOnPreDrawListener(
//            object : ViewTreeObserver.OnPreDrawListener {
//                override fun onPreDraw(): Boolean {
//                    // Check if the initial data is ready.
//                    return if (viewModel.isDataReady.get()) {
//                        // The content is ready; start drawing.
//                        content.viewTreeObserver.removeOnPreDrawListener(this)
//                        true
//                    } else {
//                        // The content is not ready; suspend.
//                        false
//                    }
//                }
//            }
//        )
    }

    private fun toNextActivity(){
        val starter = Intent(this, MainActivity::class.java)
        starter.action = intent.action
        starter.putExtras(intent)
        startActivity(starter)
        finish()
    }

    class SplashViewModel : BaseViewModel() {
        var isDataReady = AtomicBoolean(false)

        fun initData() {
            viewModelScope.launch {
                delay(2000)
                isDataReady.compareAndSet(false, true)
            }
        }
    }
}