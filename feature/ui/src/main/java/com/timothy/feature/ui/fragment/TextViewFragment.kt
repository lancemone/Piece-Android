package com.timothy.feature.ui.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.buildSpannedString
import com.timothy.common.base.BaseFragment
import com.timothy.feature.ui.databinding.FragmentTextViewBinding
import com.timothy.framework.ktx.string.span.linearGradient


/**
 * @Project: Piece
 * @ClassPath:  com.timothy.feature.ui.fragment.TextViewFragment
 * @Author: MoTao
 * @Date: 2023-07-25
 * <p>
 * <p/>
 */
class TextViewFragment : BaseFragment(){

    private val binding: FragmentTextViewBinding by lazy {
        FragmentTextViewBinding.inflate(layoutInflater)
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
        with(binding){
            firstTv.text = buildSpannedString {
                linearGradient(
                    Color.parseColor("#7F54FF"),
                    Color.parseColor("#81A4FF"),
                    Color.parseColor("#E9FCFF"),
                    Color.parseColor("#C68EFF"),
                    Color.parseColor("#BBAFFF")
                ){
                    append("Surprise Box")
                }
            }
        }
    }
}