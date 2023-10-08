package com.timothy.feature.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.core.view.setPadding
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.checkbox.MaterialCheckBox
import com.google.android.material.shape.ShapeAppearanceModel
import com.timothy.common.base.BaseFragment
import com.timothy.feature.ui.R
import com.timothy.feature.ui.databinding.FragmentShapeAbleImageviewBinding


/**
 * @Project: Piece
 * @ClassPath:  com.timothy.feature.ui.fragment.ShapeableImageViewFragment
 * @Author: MoTao
 * @Date: 2023-09-19
 * <p>
 * <p/>
 */
class ShapeableImageViewFragment : BaseFragment(), AdapterView.OnItemSelectedListener {
    private val binding: FragmentShapeAbleImageviewBinding by lazy {
        FragmentShapeAbleImageviewBinding.inflate(layoutInflater)
    }

    private val spinnerEntriesToStyle: Map<Long, Int> by lazy {
        hashMapOf(
            0L to 0,
            1L to R.style.ShapeableImageRoundedCorner16Style,
            2L to R.style.ShapeableImageTopCorner64Style,
            3L to R.style.ShapeableImageBottomCorner64Style,
            4L to R.style.ShapeableImageTopRightCorner64Style,
            5L to R.style.ShapeableImageTopLeftCorner64Style,
            6L to R.style.ShapeableImageBottomRightCorner64Style,
            7L to R.style.ShapeableImageBottomLeftCorner64Style,
            8L to R.style.ShapeableImageCircleStyle,
            9L to R.style.ShapeableImageTopCornerStyle,
            10L to R.style.ShapeableImageDiamondStyle,
            11L to R.style.ShapeableImageCutCorner16Style,
            12L to R.style.ShapeableImageLeafShapeStyle,
            13L to R.style.ShapeableImageRightTipsCornerStyle,
        )
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
        binding.spinnerShapeableStyle.onItemSelectedListener = this
        binding.spinnerShapeableStyleOverlay.onItemSelectedListener = this
        binding.checkStroke.addOnCheckedStateChangedListener { _, state ->
            when (state) {
                MaterialCheckBox.STATE_CHECKED -> {
                    binding.ivShapeAble.setStrokeColorResource(com.timothy.common.R.color.md_theme_onBackground)
                    binding.ivShapeAble.setStrokeWidthResource(com.timothy.common.R.dimen.dp_2)
                    binding.checkPadding.isChecked = true
                }

                else -> {
                    binding.ivShapeAble.strokeWidth = 0F
                    binding.checkPadding.isChecked = false
                }
            }
        }

        binding.checkPadding.addOnCheckedStateChangedListener { _, state ->
            when (state) {
                MaterialCheckBox.STATE_CHECKED -> {
                    binding.ivShapeAble.setPadding(binding.ivShapeAble.strokeWidth.toInt() / 2)
                }

                else -> {
                    binding.ivShapeAble.setPadding(0)
                }
            }
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        Log.d("Piece", "spinnerShapeableStyle onItemSelected id=$id")
        val shapeStyles: Pair<Int, Int> = when (parent?.id) {
            R.id.spinner_shapeable_style -> {
                Pair(
                    spinnerEntriesToStyle.getOrDefault(id, 0),
                    spinnerEntriesToStyle.getOrDefault(
                        binding.spinnerShapeableStyleOverlay.selectedItemId,
                        0
                    )
                )
            }

            R.id.spinner_shapeable_style_overlay -> {
                Pair(
                    spinnerEntriesToStyle.getOrDefault(
                        binding.spinnerShapeableStyle.selectedItemId,
                        0
                    ),
                    spinnerEntriesToStyle.getOrDefault(id, 0)
                )
            }

            else -> {
                Pair(0, 0)
            }
        }
        binding.ivShapeAble.apply {
            shapeAppearanceModel =
                ShapeAppearanceModel.builder(context, shapeStyles.first, shapeStyles.second).build()
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }
}