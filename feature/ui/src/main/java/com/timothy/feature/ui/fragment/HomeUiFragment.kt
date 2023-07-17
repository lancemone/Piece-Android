package com.timothy.feature.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuProvider
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.timothy.common.base.BaseFragment
import com.timothy.feature.ui.R
import com.timothy.feature.ui.databinding.FragmentHomeUiBinding
import com.timothy.feature.ui.databinding.HomeNavViewHeaderBinding


/**
 * @Project: Piece
 * @ClassPath:  com.timothy.feature.ui.fragment.HomeUiFragment
 * @Author: MoTao
 * @Date: 2023-07-12
 * <p>
 * <p/>
 */
class HomeUiFragment : BaseFragment(), MenuProvider{

    private lateinit var binding: FragmentHomeUiBinding

    private lateinit var navController: NavController

//    private val navHeaderBinding: HomeNavViewHeaderBinding by lazy {
//        HomeNavViewHeaderBinding.inflate(layoutInflater)
//    }

//    private val appBarConfiguration: AppBarConfiguration by lazy {
////        AppBarConfiguration(
////            topLevelDestinationIds = setOf(R.id.fragmentAppHomeUI),
////            drawerLayout = binding.homeDrawer
////        ){
////            true
////        }
//        AppBarConfiguration(navController.graph, binding.homeDrawer)
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setHasOptionsMenu(true)
    }

//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        inflater.inflate(R.menu.menu_fragment_ui_bar, menu)
//        super.onCreateOptionsMenu(menu, inflater)
//    }

    private var hasAddHeader = false
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        activity?.addMenuProvider(this, viewLifecycleOwner)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home_ui, container, false)
//        if (!hasAddHeader){
//            binding.navView.addHeaderView(navHeaderBinding.root)
//            hasAddHeader = true
//        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
//        binding.homeToolBar.setupWithNavController(navController = navController, configuration = appBarConfiguration)
//        binding.navView.setupWithNavController(navController)
    }

    override fun onPrepareMenu(menu: Menu) {
        super.onPrepareMenu(menu)
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
//        menuInflater.inflate(R.menu.menu_fragment_ui_bar, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        Log.d("Piece", "onMenuItemSelected ${menuItem.title}")
        return true
    }
}