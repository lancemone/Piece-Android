package com.timothy.feature.ui.fragment

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.timothy.common.base.BaseFragment
import com.timothy.feature.ui.R
import com.timothy.feature.ui.databinding.FragmentLinearLayoutBinding


/**
 * @Project: Piece
 * @ClassPath:  com.timothy.feature.ui.fragment.LinearLayoutFragment
 * @Author: MoTao
 * @Date: 2023-07-07
 * <p>
 * <p/>
 */
class LinearLayoutFragment : BaseFragment(){

    private val binding: FragmentLinearLayoutBinding by lazy {
        FragmentLinearLayoutBinding.inflate(layoutInflater)
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
        bindViewListener()
    }

    private fun bindViewListener(){
        with(binding){
            gravityCenter.setOnClickListener {
                llSimple.gravity = Gravity.CENTER
                gravityTip.setText(R.string.gravity_center_specification)
            }
            gravityCenterHorizontal.setOnClickListener {
                llSimple.gravity = Gravity.CENTER_HORIZONTAL
                gravityTip.setText(R.string.center_horizontal_specification)
            }
            gravityCenterVertical.setOnClickListener {
                llSimple.gravity = Gravity.CENTER_VERTICAL
                gravityTip.setText(R.string.center_vertical_specification)
            }
            gravityStart.setOnClickListener {
                llSimple.gravity = Gravity.START
                gravityTip.setText(R.string.gravity_start_specification)
            }
            gravityEnd.setOnClickListener {
                llSimple.gravity = Gravity.END
                gravityTip.setText(R.string.gravity_end_specification)
            }
            gravityTop.setOnClickListener {
                llSimple.gravity = Gravity.TOP
                gravityTip.setText(R.string.gravity_top_specification)
            }
            gravityBottom.setOnClickListener {
                llSimple.gravity = Gravity.BOTTOM
                gravityTip.setText(R.string.gravity_bottom_specification)
            }
        }
    }
}