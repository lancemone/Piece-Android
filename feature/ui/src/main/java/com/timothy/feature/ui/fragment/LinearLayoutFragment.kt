package com.timothy.feature.ui.fragment

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import androidx.core.animation.addListener
import androidx.core.animation.doOnEnd
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

        binding.root.post {
            val translateAnimator1 = ObjectAnimator.ofFloat(binding.tvAnim1, "translationX", -binding.tvAnim1.right.toFloat(), 0F)
            val translateAnimator2 = ObjectAnimator.ofFloat(binding.tvAnim2, "translationX", (binding.root.width - binding.tvAnim2.left).toFloat(), 0F)
            val scaleXAnimator1 = ObjectAnimator.ofFloat(binding.tvAnim1, "scaleX", 1F, 1.5F, 1F)
            val scaleYAnimator1 = ObjectAnimator.ofFloat(binding.tvAnim1, "scaleY", 1F, 1.5F, 1F)
            val scaleXAnimator2 = ObjectAnimator.ofFloat(binding.tvAnim2, "scaleX", 1F, 1.5F, 1F)
            val scaleYAnimator2 = ObjectAnimator.ofFloat(binding.tvAnim2, "scaleY", 1F, 1.5F, 1F)
            Log.d("motao", "tvAnim1.right=${binding.tvAnim1.right} tvAnim2.right=${binding.tvAnim2.right} root.width=${binding.root.width}")

            val animator1 = AnimatorSet().apply {
                play(scaleYAnimator1)
                    .with(scaleXAnimator1)
                    .after(translateAnimator1)
                interpolator = LinearInterpolator()
                duration = 1500
            }
            val animator2 = AnimatorSet().apply {
                play(scaleYAnimator2)
                    .with(scaleXAnimator2)
                    .after(translateAnimator2)
                interpolator = LinearInterpolator()
                duration = 1500
            }
            animator1.start()
            animator2.start()
//            val animator = AnimatorSet().apply {
//                playTogether(animator1, animator2)
//                interpolator = LinearInterpolator()
//            }
//            animator.addListener(
//                onStart = {
//                    Log.d("motao", "animator start")
//                }
//            )
//            animator.startDelay = 500
//            animator.start()
        }
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