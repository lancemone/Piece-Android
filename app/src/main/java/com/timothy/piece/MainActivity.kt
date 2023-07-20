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
import androidx.core.view.isVisible
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.timothy.common.base.BaseActivity
import com.timothy.common.lifecyc.FragmentLifecycleManager
import com.timothy.piece.databinding.ActivityMainBinding
import com.timothy.piece.databinding.LayoutHomeNavigationHeaderBinding
import com.timothy.piece.vm.MainViewModel

//@Route(path = RouterPath.path_app_main)
class MainActivity : BaseActivity(), SplashScreen.OnExitAnimationListener {

    private val viewModel: MainViewModel by viewModels<MainViewModel>()

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val navHeaderBinding: LayoutHomeNavigationHeaderBinding by lazy {
        LayoutHomeNavigationHeaderBinding.inflate(layoutInflater)
    }

    private lateinit var navController: NavController

    private var mDestinationChangedListener: NavController.OnDestinationChangedListener =
        NavController.OnDestinationChangedListener { controller, destination, _ ->
            Toast.makeText(
                controller.context,
                "destination=${destination.label}",
                Toast.LENGTH_SHORT
            ).show()
            // 顶级导航时使用activity的ToolBar
        binding.hostToolBar.isVisible = appBarConfiguration.isTopLevelDestination(destination=destination)

        binding.hostDrawer.setDrawerLockMode(   // 非顶级导航页面时锁定Drawer
            if (appBarConfiguration.isTopLevelDestination(destination = destination))
                DrawerLayout.LOCK_MODE_UNLOCKED
            else
                DrawerLayout.LOCK_MODE_LOCKED_CLOSED
        )
            Log.d("Route", "onDestinationChanged destination=$destination")
        }

    private val appBarConfiguration: AppBarConfiguration by lazy {
        AppBarConfiguration(navController.graph, binding.hostDrawer)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
//        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
//        setSupportActionBar(binding.hostToolBar)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        navController.addOnDestinationChangedListener(mDestinationChangedListener)
        navHostFragment.childFragmentManager.registerFragmentLifecycleCallbacks(
            FragmentLifecycleManager.get(),
            false
        )
        viewModel.initData()
        //        splashScreen.setOnExitAnimationListener(this)
//        splashScreen.setKeepOnScreenCondition {
//            Log.d("Routing", "Routing splashScreen KeepOnScreenCondition")
//            !viewModel.isDataReady.get()
//        }

        supportFragmentManager.registerFragmentLifecycleCallbacks(
            FragmentLifecycleManager.get(),
            false
        )
        binding.navView.addHeaderView(navHeaderBinding.root)
        binding.navView.setupWithNavController(navController)
        binding.hostToolBar.setupWithNavController(
            navController = navController,
            configuration = appBarConfiguration
        )

        navController.navigate(R.id.action_main_to_authentication)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        supportFragmentManager.unregisterFragmentLifecycleCallbacks(FragmentLifecycleManager.get())
        navController.removeOnDestinationChangedListener(mDestinationChangedListener)
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

    companion object
}