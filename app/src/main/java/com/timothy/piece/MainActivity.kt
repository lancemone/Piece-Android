package com.timothy.piece

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AnticipateInterpolator
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreenViewProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.timothy.common.base.BaseActivity
import com.timothy.common.lifecyc.FragmentLifecycleManager
import com.timothy.framework.ktx.utils.StatusBarUtil
import com.timothy.piece.databinding.ActivityMainBinding
import com.timothy.piece.vm.MainViewModel

//@Route(path = RouterPath.path_app_main)
class MainActivity : BaseActivity(), SplashScreen.OnExitAnimationListener {

    private val viewModel: MainViewModel by viewModels<MainViewModel>()

//    private lateinit var navController: NavController

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
//        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        StatusBarUtil.setTransparent(this)
        val fragment = supportFragmentManager.findFragmentById(R.id.main_content) as NavHostFragment
        navController = fragment.navController
        fragment.childFragmentManager.registerFragmentLifecycleCallbacks(
            FragmentLifecycleManager.get(),
            false
        )
        viewModel.initData()
        //        splashScreen.setOnExitAnimationListener(this)
//        splashScreen.setKeepOnScreenCondition {
//            Log.d("Routing", "Routing splashScreen KeepOnScreenCondition")
//            !viewModel.isDataReady.get()
//        }

//        supportFragmentManager.registerFragmentLifecycleCallbacks(
//            FragmentLifecycleManager.get(),
//            false
//        )

        navController?.addOnDestinationChangedListener(mDestinationChangedListener)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        supportFragmentManager.unregisterFragmentLifecycleCallbacks(FragmentLifecycleManager.get())
        navController?.removeOnDestinationChangedListener(mDestinationChangedListener)
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
        }

        // Run your animation.
        slideUp.start()
    }

    companion object{
        var navController: NavController? = null
        private val mDestinationChangedListener =
            NavController.OnDestinationChangedListener { controller, destination, arguments ->
                Toast.makeText(controller.context, "destination=${destination.label}", Toast.LENGTH_SHORT).show()
                Log.d("Route", "onDestinationChanged destination=$destination")
            }
    }
}