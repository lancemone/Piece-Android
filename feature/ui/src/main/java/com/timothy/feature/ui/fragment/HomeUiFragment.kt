package com.timothy.feature.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.timothy.common.base.BaseFragment
import com.timothy.feature.ui.R
import com.timothy.feature.ui.databinding.FragmentHomeUiBinding


/**
 * @Project: Piece
 * @ClassPath:  com.timothy.feature.ui.fragment.HomeUiFragment
 * @Author: MoTao
 * @Date: 2023-07-12
 * <p>
 * <p/>
 */
class HomeUiFragment : BaseFragment() {

    private val binding: FragmentHomeUiBinding by lazy {
        FragmentHomeUiBinding.inflate(layoutInflater)
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
        bindListener()
    }

    private fun bindListener() {
        with(binding) {
            binding.tvLinear.setOnClickListener {
                findNavController().navigate(R.id.actionToLinearLayoutFragment)
            }
        }
    }
}