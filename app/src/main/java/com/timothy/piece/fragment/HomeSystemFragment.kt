package com.timothy.piece.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.timothy.common.base.BaseFragment
import com.timothy.piece.databinding.FragmentHomeSystemBinding


/**
 * @Project: Piece
 * @ClassPath:  com.timothy.piece.fragment.HomeSystemFragment
 * @Author: MoTao
 * @Date: 2023-07-12
 * <p>
 * <p/>
 */
class HomeSystemFragment: BaseFragment() {

    private val binding: FragmentHomeSystemBinding by lazy {
        FragmentHomeSystemBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }
}