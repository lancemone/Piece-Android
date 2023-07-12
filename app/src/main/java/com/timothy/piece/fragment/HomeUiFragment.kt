package com.timothy.piece.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.timothy.common.base.BaseFragment
import com.timothy.piece.databinding.FragmentHomeUiBinding


/**
 * @Project: Piece
 * @ClassPath:  com.timothy.piece.fragment.HomeUiFragment
 * @Author: MoTao
 * @Date: 2023-07-12
 * <p>
 * <p/>
 */
class HomeUiFragment : BaseFragment(){

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
}