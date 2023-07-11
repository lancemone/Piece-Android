package com.timothy.piece.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.navigation.NavigationBarView
import com.timothy.common.base.BaseFragment
import com.timothy.common.fragment.ErrorFragment
import com.timothy.piece.R
import com.timothy.piece.databinding.FragmentMainBinding


/**
 * @Project: Piece
 * @ClassPath:  com.timothy.piece.fragment.MainFragment
 * @Author: MoTao
 * @Date: 2023-05-31
 * <p>
 * <p/>
 */
class MainFragment : BaseFragment(), NavigationBarView.OnItemSelectedListener {

    private val binding: FragmentMainBinding by lazy {
        FragmentMainBinding.inflate(layoutInflater)
    }

    private val mainUiFragment: MainUiFragment by lazy {
        MainUiFragment()
    }

    private val mainSystemFragment: MainSystemFragment by lazy {
        MainSystemFragment()
    }

    private val mainOtherFragment: MainOtherFragment by lazy {
        MainOtherFragment()
    }

    private val mainDataFragment: MainDataFragment by lazy {
        MainDataFragment()
    }

    private var currentItem: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView(){
        bindVPToNav()
        binding.mainBottomNav.getChildAt(currentItem).isSelected = true
    }

    private fun bindVPToNav(){
        binding.vpMain.adapter = object : FragmentStateAdapter(this){
            override fun getItemCount(): Int {
                return 4
            }

            override fun createFragment(position: Int): Fragment {
                return when(position){
                    0 -> { mainUiFragment }
                    1 -> { mainSystemFragment }
                    2 -> { mainOtherFragment }
                    3 -> { mainDataFragment }
                    else -> { ErrorFragment() }
                }
            }

        }
        binding.mainBottomNav.setOnItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.main_tab_home -> {
                currentItem = 0
            }
            R.id.main_tab_compose -> {
                currentItem = 1
            }
            R.id.main_tab_communicate -> {
                currentItem = 2
            }
            R.id.main_tab_components -> {
                currentItem = 3
            }
        }
        binding.vpMain.currentItem = currentItem
        return true
    }
}