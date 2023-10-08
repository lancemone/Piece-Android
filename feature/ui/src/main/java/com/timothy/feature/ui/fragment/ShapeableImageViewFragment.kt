package com.timothy.feature.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.timothy.common.base.BaseFragment
import com.timothy.feature.ui.databinding.FragmentShapeAbleImageviewBinding


/**
 * @Project: Piece
 * @ClassPath:  com.timothy.feature.ui.fragment.ShapeableImageViewFragment
 * @Author: MoTao
 * @Date: 2023-09-19
 * <p>
 * <p/>
 */
class ShapeableImageViewFragment : BaseFragment(){
    private val binding: FragmentShapeAbleImageviewBinding by lazy {
        FragmentShapeAbleImageviewBinding.inflate(layoutInflater)
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
        binding.toolBar.setupWithNavController(findNavController())
    }
}