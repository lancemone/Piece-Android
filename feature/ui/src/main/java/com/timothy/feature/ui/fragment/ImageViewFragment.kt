package com.timothy.feature.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.timothy.common.base.BaseFragment
import com.timothy.feature.ui.databinding.FragmentImageViewBinding

class ImageViewFragment : BaseFragment(){

    private val binding: FragmentImageViewBinding by lazy {
        FragmentImageViewBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}